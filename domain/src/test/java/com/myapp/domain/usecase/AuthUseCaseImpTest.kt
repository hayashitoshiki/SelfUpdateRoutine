package com.myapp.domain.usecase

import com.myapp.domain.dto.AllReportInputDto
import com.myapp.domain.dto.AuthInputDto
import com.myapp.domain.dto.SignInDto
import com.myapp.domain.model.value.Email
import com.myapp.domain.model.value.Password
import com.myapp.domain.repository.LocalMissionStatementRepository
import com.myapp.domain.repository.LocalReportRepository
import com.myapp.domain.repository.RemoteAccountRepository
import com.myapp.domain.repository.RemoteReportRepository
import com.myapp.domain.translator.ReportTranslator
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

/**
 * 認証機能　ロジック仕様
 *
 */
class AuthUseCaseImpTest {

    private lateinit var authUseCase: AuthUseCase
    private lateinit var remoteAccountRepository: RemoteAccountRepository
    private lateinit var remoteReportRepository: RemoteReportRepository
    private lateinit var localReportRepository: LocalReportRepository
    private lateinit var localMissionStatementRepository: LocalMissionStatementRepository

    private val email = "123@nr.jp"
    private val emailVo = Email("123@nr.jp")
    private val password = "123Abc"
    private val passwordVo = Password("123Abc")
    private val signInDto = SignInDto(email, password)
    private val authDto = AuthInputDto(emailVo, passwordVo)
    private val expectedError1 = IllegalAccessError("失敗1")
    private val expectedError2 = IllegalAccessError("失敗2")
    private val expectedError3 = IllegalAccessError("失敗3")
    private val expectedError4 = IllegalAccessError("失敗4")
    private val expectedError5 = IllegalAccessError("失敗5")
    private val expectedError6 = IllegalAccessError("失敗6")
    private val expectedError7 = IllegalAccessError("失敗7")
    private val expectedError8 = IllegalAccessError("失敗8")
    private val expectedError9 = IllegalAccessError("失敗9")
    private val expectedError10 = IllegalAccessError("失敗10")

    private val reportDto = AllReportInputDto("fact", "find", "learn", "statement", 40, "reason", "improve")
    private val report = ReportTranslator.allReportConvert(reportDto)
    private val reportList = listOf(report)

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    // UseCase初期化
    private fun initUseCase() {
        authUseCase = AuthUseCaseImp(remoteAccountRepository, remoteReportRepository, localReportRepository, localMissionStatementRepository)
    }

    // ログイン状態設定
    private fun setStateSignInMock() {
        remoteAccountRepository = mockk<RemoteAccountRepository>().also {
            every { it.autoAuth() } returns true
            coEvery { it.signIn(email, password) } throws expectedError1
            coEvery { it.signUp(emailVo, passwordVo) } throws expectedError2
            coEvery { it.signOut() } returns Unit
            coEvery { it.delete() } returns Unit
        }
    }

    // ログアウト状態設定
    private fun setStateSignOutMock() {
        remoteAccountRepository = mockk<RemoteAccountRepository>().also {
            every { it.autoAuth() } returns false
            coEvery { it.signIn(email, password) } returns Unit
            coEvery { it.signUp(emailVo, passwordVo) } returns Unit
            coEvery { it.signOut() } throws expectedError3
            coEvery { it.delete() } throws expectedError4
        }
    }

    // データベースアクセエス成功
    private fun setRemoteDatabaseAccessTrue() {
        remoteReportRepository = mockk<RemoteReportRepository>().also {
            coEvery { it.getAllReport(any()) } returns reportList
            coEvery { it.saveReport(any(), any()) } returns Unit
        }
    }

    //　データベースアクセス不可
    private fun setRemoteDatabaseAccessFalse() {
        remoteReportRepository = mockk<RemoteReportRepository>().also {
            coEvery { it.getAllReport(any()) } throws expectedError5
            coEvery { it.saveReport(any(), any()) } throws expectedError6
        }
    }

    // データベースアクセエス成功
    private fun setLocalDatabaseAccessTrue() {
        localReportRepository = mockk<LocalReportRepository>().also {
            coEvery { it.saveReport(any()) } returns Unit
            coEvery { it.getAllReport() } returns reportList
            coEvery { it.deleteAll() } returns Unit
        }
        localMissionStatementRepository = mockk<LocalMissionStatementRepository>().also {
            coEvery { it.deleteAll() } returns Unit
        }
    }

    //　データベースアクセス不可
    private fun setLocalDatabaseAccessFalse() {
        localReportRepository = mockk<LocalReportRepository>().also {
            coEvery { it.saveReport(any()) } throws expectedError7
            coEvery { it.getAllReport() } throws expectedError8
            coEvery { it.deleteAll() } throws expectedError9
        }
        localMissionStatementRepository = mockk<LocalMissionStatementRepository>().also {
            coEvery { it.deleteAll() } throws expectedError10
        }
    }

    // region 自動認証

    /**
     * 自動認証判定
     *
     * 条件：ログイン状態
     * 期待結果：ログイン済み(true)となること
     */
    @Test
    fun isSignInByTrue() {
        setStateSignInMock()
        setRemoteDatabaseAccessTrue()
        setLocalDatabaseAccessTrue()
        initUseCase()

        val result = authUseCase.autoAuth()

        assertEquals(true, result)
    }

    /**
     * 自動認証判定
     *
     * 条件：未ログイン状態
     * 期待結果：未ログイン(false)となること
     */
    @Test
    fun isSignInByFalse() {
        setStateSignOutMock()
        setRemoteDatabaseAccessTrue()
        setLocalDatabaseAccessTrue()
        initUseCase()

        val result = authUseCase.autoAuth()

        assertEquals(false, result)
    }

    // endregion

    // region ログイン

    /**
     * ログイン処理
     *
     * 条件：未ログイン状態(Exceptionが返らない状態)
     * 期待結果：
     * ・Exceptionとならないこと
     * ・入力値で外部へのログイン処理が１回呼ばれること
     * ・リモートデータ取得処理が１回呼ばれること
     * ・ローカルデータ反映処理が１回呼ばれること
     */
    @Test
    fun signInByStateNotSignIn() = runBlocking {
        setStateSignOutMock()
        setRemoteDatabaseAccessTrue()
        setLocalDatabaseAccessTrue()
        initUseCase()

        authUseCase.signIn(signInDto)

        coVerify(exactly = 1) { (remoteAccountRepository).signIn(email, password) }
        coVerify(exactly = 1) { (remoteReportRepository).getAllReport(email) }
        coVerify(exactly = 1) { (localReportRepository).saveReport(report) }
    }

    /**
     * ログイン
     *
     * 条件：ログイン状態(Exceptionが返る状態)
     * 期待結果：
     * ・Exceptionとなること
     * ・入力値で外部へのログイン処理が１回呼ばれること
     * ・リモートデータ取得処理が呼ばれないこと
     * ・ローカルデータ反映処理が呼ばれないこと
     */
    @Test
    fun signInByStateSignIn() = runBlocking {
        setStateSignInMock()
        setRemoteDatabaseAccessTrue()
        setLocalDatabaseAccessTrue()
        initUseCase()

        runCatching { authUseCase.signIn(signInDto) }
            .onSuccess { fail() }
            .onFailure { assertEquals(expectedError1.message, it.message) }

        coVerify(exactly = 1) { (remoteAccountRepository).signIn(email, password) }
        coVerify(exactly = 0) { (remoteReportRepository).getAllReport(email) }
        coVerify(exactly = 0) { (localReportRepository).saveReport(report) }
    }

    /**
     * ログイン
     *
     * 条件：ログイン後リモートレポート取得でエラーが発生
     * 期待結果：
     * ・Exceptionとならないこと
     * ・入力値で外部へのログイン処理が１回呼ばれること
     * ・リモートデータ取得処理が１回呼ばれること
     * ・ローカルデータ反映処理が呼ばれないこと
     */
    @Test
    fun signInByRemoteDbException() = runBlocking {
        setStateSignOutMock()
        setRemoteDatabaseAccessFalse()
        setLocalDatabaseAccessTrue()
        initUseCase()

        authUseCase.signIn(signInDto)

        coVerify(exactly = 1) { (remoteAccountRepository).signIn(email, password) }
        coVerify(exactly = 1) { (remoteReportRepository).getAllReport(email) }
        coVerify(exactly = 0) { (localReportRepository).saveReport(report) }
    }

    /**
     * ログイン
     *
     * 条件：ログイン後ローカルレポート反映でエラーが発生
     * 期待結果：
     * ・Exceptionとならないこと
     * ・入力値で外部へのログイン処理が１回呼ばれること
     * ・リモートデータ取得処理が１回呼ばれること
     * ・ローカルデータ反映処理が呼ばれないこと
     */
    @Test
    fun signInByLocalDbException() = runBlocking {
        setStateSignOutMock()
        setRemoteDatabaseAccessTrue()
        setLocalDatabaseAccessFalse()
        initUseCase()

        authUseCase.signIn(signInDto)

        coVerify(exactly = 1) { (remoteAccountRepository).signIn(email, password) }
        coVerify(exactly = 1) { (remoteReportRepository).getAllReport(email) }
        coVerify(exactly = 1) { (localReportRepository).saveReport(report) }
    }

    // endregion

    // region ログアウト

    /**
     * ログアウト処理
     *
     * 条件：ログイン状態(Exceptionが返らない状態)
     * 期待結果：
     * ・Exceptionとならないこと
     * ・外部へのログアウト処理が１回呼ばれること
     * ・ローカルデータ削除処理が１回呼ばれること
     */
    @Test
    fun signOutByStateSignIn() = runBlocking {
        setStateSignInMock()
        setRemoteDatabaseAccessTrue()
        setLocalDatabaseAccessTrue()
        initUseCase()

        authUseCase.signOut()

        coVerify(exactly = 1) { (remoteAccountRepository).signOut() }
        coVerify(exactly = 1) { (localReportRepository).deleteAll() }
    }

    /**
     * ログアウト処理
     *
     * 条件：未ログイン状態(Exceptionが返る状態)
     * 期待結果：
     * ・Exceptionとなること
     * ・外部へのログアウト処理が１回呼ばれること
     * ・ローカルデータ削除処理が呼ばれないこと
     */
    @Test
    fun signOutByStateNotSignIn() = runBlocking {
        setStateSignOutMock()
        setRemoteDatabaseAccessTrue()
        setLocalDatabaseAccessTrue()
        initUseCase()

        runCatching { authUseCase.signOut() }
            .onSuccess { fail() }
            .onFailure { assertEquals(expectedError3.message, it.message) }

        coVerify(exactly = 1) { (remoteAccountRepository).signOut() }
        coVerify(exactly = 0) { (localReportRepository).deleteAll() }
    }

    /**
     * ログアウト処理
     *
     * 条件：ローカルデータアクセスでException
     * 期待結果：
     * ・Exceptionとならないこと
     * ・外部へのログアウト処理が１回呼ばれること
     * ・ローカルデータ削除処理が１回呼ばれること
     */
    @Test
    fun signOutByLocalDbException() = runBlocking {
        setStateSignInMock()
        setRemoteDatabaseAccessTrue()
        setLocalDatabaseAccessTrue()
        initUseCase()

        authUseCase.signOut()

        coVerify(exactly = 1) { (remoteAccountRepository).signOut() }
        coVerify(exactly = 1) { (localReportRepository).deleteAll() }
    }

    // endregion

    // region アカウント作成

    /**
     * アカウント作成処理
     *
     * 条件：未ログイン状態(Exceptionが返らない状態)
     * 期待結果：
     * ・Exceptionとならないこと
     * ・入力値で外部へのアカウント作成処理が１回呼ばれること
     * ・ローカルデータ取得処理が１回呼ばれること
     * ・リモートデータ反映処理が１回呼ばれること
     */
    @Test
    fun signUpByStateNotSignIn() = runBlocking {
        setStateSignOutMock()
        setRemoteDatabaseAccessTrue()
        setLocalDatabaseAccessTrue()
        initUseCase()

        authUseCase.signUp(authDto)

        coVerify(exactly = 1) { (remoteAccountRepository).signUp(emailVo, passwordVo) }
        coVerify(exactly = 1) { (localReportRepository).getAllReport() }
        coVerify(exactly = 1) { (remoteReportRepository).saveReport(reportList, emailVo.value) }
    }

    /**
     * アカウント作成処理
     *
     * 条件：ログイン状態(Exceptionが返る状態)
     * 期待結果：
     * ・Exceptionとなること
     * ・入力値で外部へのアカウント作成処理が１回呼ばれること
     * ・ローカルデータ取得処理が呼ばれないこと
     * ・リモートデータ反映処理が呼ばれないこと
     */
    @Test
    fun signUpByStateSignIn() = runBlocking {
        val email = Email("123@nr.jp")
        val password = Password("123Abc")
        val dto = AuthInputDto(email, password)
        setStateSignInMock()
        setRemoteDatabaseAccessTrue()
        setLocalDatabaseAccessTrue()
        initUseCase()

        runCatching { authUseCase.signUp(dto) }
            .onSuccess { fail() }
            .onFailure { assertEquals(expectedError2.message, it.message) }

        coVerify(exactly = 1) { (remoteAccountRepository).signUp(email, password) }
        coVerify(exactly = 0) { (localReportRepository).getAllReport() }
        coVerify(exactly = 0) { (remoteReportRepository).saveReport(reportList, emailVo.value) }
    }

    /**
     * アカウント作成処理
     *
     * 条件：アカウント作成後ローカルレポート取得でException
     * 期待結果：
     * ・Exceptionとならないこと
     * ・入力値で外部へのアカウント作成処理が１回呼ばれること
     * ・ローカルデータ取得処理が１回呼ばれること
     * ・リモートデータ反映処理が呼ばれないこと
     */
    @Test
    fun signUpByLocalDbException() = runBlocking {
        setStateSignOutMock()
        setRemoteDatabaseAccessTrue()
        setLocalDatabaseAccessFalse()
        initUseCase()

        authUseCase.signUp(authDto)

        coVerify(exactly = 1) { (remoteAccountRepository).signUp(emailVo, passwordVo) }
        coVerify(exactly = 1) { (localReportRepository).getAllReport() }
        coVerify(exactly = 0) { (remoteReportRepository).saveReport(reportList, emailVo.value) }
    }

    /**
     * アカウント作成処理
     *
     * 条件：アカウント作成後リモートレポート反映でException
     * 期待結果：
     * ・Exceptionとならないこと
     * ・入力値で外部へのアカウント作成処理が１回呼ばれること
     * ・ローカルデータ取得処理が１回呼ばれること
     * ・リモートデータ反映処理が１回呼ばれること
     */
    @Test
    fun signUpByRemoteDbException() = runBlocking {
        setStateSignOutMock()
        setRemoteDatabaseAccessFalse()
        setLocalDatabaseAccessTrue()
        initUseCase()

        authUseCase.signUp(authDto)

        coVerify(exactly = 1) { (remoteAccountRepository).signUp(emailVo, passwordVo) }
        coVerify(exactly = 1) { (localReportRepository).getAllReport() }
        coVerify(exactly = 1) { (remoteReportRepository).saveReport(reportList, emailVo.value) }
    }

    // endregion

    // region アカウント削除

    /**
     * アカウント削除処理
     *
     * 条件：ログイン状態(Exceptionが返らない状態)
     * 期待結果：
     * ・Exceptionとならないこと
     * ・外部へのアカウント削除処理が１回呼ばれること
     * ・ローカルデータ削除処理が１回呼ばれること
     */
    @Test
    fun accountDeleteByStateSignIn() = runBlocking {
        setStateSignInMock()
        setRemoteDatabaseAccessTrue()
        setLocalDatabaseAccessTrue()
        initUseCase()

        authUseCase.delete()

        coVerify(exactly = 1) { (remoteAccountRepository).delete() }
        coVerify(exactly = 1) { (localReportRepository).deleteAll() }
        coVerify(exactly = 1) { (localMissionStatementRepository).deleteAll() }
    }

    /**
     * アカウント削除処理
     *
     * 条件：未ログイン状態(Exceptionが返る状態)
     * 期待結果：
     * ・Exceptionとなること
     * ・外部へのアカウント削除処理が１回呼ばれること
     * ・ローカルデータ削除処理が呼ばれないこと
     */
    @Test
    fun accountDeleteByStateNotSignIn() = runBlocking {
        setStateSignOutMock()
        setRemoteDatabaseAccessTrue()
        setLocalDatabaseAccessTrue()
        initUseCase()

        runCatching { authUseCase.delete() }
            .onSuccess { fail() }
            .onFailure { assertEquals(expectedError4.message, it.message) }

        coVerify(exactly = 1) { (remoteAccountRepository).delete() }
        coVerify(exactly = 0) { (localReportRepository).deleteAll() }
        coVerify(exactly = 0) { (localMissionStatementRepository).deleteAll() }
    }

    /**
     * アカウント削除処理
     *
     * 条件：アカウント削除後ローカルデータ削除でException
     * 期待結果：
     * ・Exceptionとならないこと
     * ・外部へのアカウント削除処理が１回呼ばれること
     * ・ローカルデータ削除処理が１回呼ばれること
     */
    @Test
    fun accountDeleteByLocalDbException() = runBlocking {
        setStateSignInMock()
        setRemoteDatabaseAccessTrue()
        setLocalDatabaseAccessFalse()
        initUseCase()

        authUseCase.delete()

        coVerify(exactly = 1) { (remoteAccountRepository).delete() }
        coVerify(exactly = 1) { (localReportRepository).deleteAll() }
        coVerify(exactly = 0) { (localMissionStatementRepository).deleteAll() }
    }

    // endregion

}