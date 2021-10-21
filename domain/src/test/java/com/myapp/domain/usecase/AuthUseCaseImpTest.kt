package com.myapp.domain.usecase

import com.myapp.domain.dto.AuthInputDto
import com.myapp.domain.model.value.Email
import com.myapp.domain.model.value.Password
import com.myapp.domain.repository.RemoteAccountRepository
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

    private val email = Email("123@nr.jp")
    private val password = Password("123Abc")
    private val dto = AuthInputDto(email, password)
    private val expectedError1 = IllegalAccessError("失敗1")
    private val expectedError2 = IllegalAccessError("失敗2")
    private val expectedError3 = IllegalAccessError("失敗3")
    private val expectedError4 = IllegalAccessError("失敗4")

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    // UseCase初期化
    private fun initUseCase() {
        authUseCase = AuthUseCaseImp(remoteAccountRepository)
    }

    // ログイン状態設定
    private fun setStateSignInMock() {
        remoteAccountRepository = mockk<RemoteAccountRepository>().also {
            every { it.autoAuth() } returns true
            coEvery { it.signIn(email, password) } throws expectedError1
            coEvery { it.signUp(email, password) } throws expectedError2
            coEvery { it.signOut() } returns Unit
            coEvery { it.delete() } returns Unit
        }
    }

    // ログアウト状態設定
    private fun setStateSignOutMock() {
        remoteAccountRepository = mockk<RemoteAccountRepository>().also {
            every { it.autoAuth() } returns false
            coEvery { it.signIn(email, password) } returns Unit
            coEvery { it.signUp(email, password) } returns Unit
            coEvery { it.signOut() } throws expectedError3
            coEvery { it.delete() } throws expectedError4
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
     */
    @Test
    fun signInByStateNotSignIn() = runBlocking {
        val email = Email("123@nr.jp")
        val password = Password("123Abc")
        val dto = AuthInputDto(email, password)
        setStateSignOutMock()
        initUseCase()

        authUseCase.signIn(dto)

        coVerify(exactly = 1) { (remoteAccountRepository).signIn(email, password) }
    }

    /**
     * ログイン
     *
     * 条件：ログイン状態(Exceptionが返る状態)
     * 期待結果：
     * ・Exceptionとなること
     * ・入力値で外部へのログイン処理が１回呼ばれること
     */
    @Test
    fun signInByStateSignIn() = runBlocking {
        val email = Email("123@nr.jp")
        val password = Password("123Abc")
        val dto = AuthInputDto(email, password)
        setStateSignInMock()
        initUseCase()

        runCatching { authUseCase.signIn(dto) }
            .onSuccess { fail() }
            .onFailure { assertEquals(expectedError1.message, it.message) }


        coVerify(exactly = 1) { (remoteAccountRepository).signIn(email, password) }
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
     */
    @Test
    fun signOutByStateSignIn() = runBlocking {
        setStateSignInMock()
        initUseCase()

        authUseCase.signOut()

        coVerify(exactly = 1) { (remoteAccountRepository).signOut() }
    }

    /**
     * ログアウト処理
     *
     * 条件：未ログイン状態(Exceptionが返る状態)
     * 期待結果：
     * ・Exceptionとなること
     * ・外部へのログアウト処理が１回呼ばれること
     */
    @Test
    fun signOutByStateNotSignIn() = runBlocking {
        setStateSignOutMock()
        initUseCase()

        runCatching { authUseCase.signOut() }
            .onSuccess { fail() }
            .onFailure { assertEquals(expectedError3.message, it.message) }

        coVerify(exactly = 1) { (remoteAccountRepository).signOut() }
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
     */
    @Test
    fun signUpByStateNotSignIn() = runBlocking {
        setStateSignOutMock()
        initUseCase()

        authUseCase.signUp(dto)

        coVerify(exactly = 1) { (remoteAccountRepository).signUp(email, password) }
    }

    /**
     * アカウント作成処理
     *
     * 条件：ログイン状態(Exceptionが返る状態)
     * 期待結果：
     * ・Exceptionとなること
     * ・入力値で外部へのアカウント作成処理が１回呼ばれること
     */
    @Test
    fun signUpByStateSignIn() = runBlocking {
        val email = Email("123@nr.jp")
        val password = Password("123Abc")
        val dto = AuthInputDto(email, password)
        setStateSignInMock()
        initUseCase()

        runCatching { authUseCase.signUp(dto) }
            .onSuccess { fail() }
            .onFailure { assertEquals(expectedError2.message, it.message) }

        coVerify(exactly = 1) { (remoteAccountRepository).signUp(email, password) }
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
     */
    @Test
    fun accountDeleteByStateSignIn() = runBlocking {
        setStateSignInMock()
        initUseCase()

        authUseCase.delete()

        coVerify(exactly = 1) { (remoteAccountRepository).delete() }
    }

    /**
     * アカウント削除処理
     *
     * 条件：未ログイン状態(Exceptionが返る状態)
     * 期待結果：
     * ・Exceptionとなること
     * ・外部へのアカウント削除処理が１回呼ばれること
     */
    @Test
    fun accountDeleteByStateNotSignIn() = runBlocking {
        setStateSignOutMock()
        initUseCase()

        runCatching { authUseCase.delete() }
            .onSuccess { fail() }
            .onFailure { assertEquals(expectedError4.message, it.message) }

        coVerify(exactly = 1) { (remoteAccountRepository).delete() }
    }

    // endregion

}