package com.myapp.data.remote

import com.myapp.data.remote.api.FireBaseService
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
 * 外部に対するアカウント関連処理 ロジック仕様
 *
 */
class RemoteAccountRepositoryImpTest {

    private lateinit var firebaseService: FireBaseService
    private lateinit var remoteAccountRepository: RemoteAccountRepository

    private val email = Email("123@nr.jp")
    private val password = Password("123Abc")
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
        remoteAccountRepository = RemoteAccountRepositoryImp(firebaseService)
    }

    // ログイン状態設定
    private fun setCallBackTrueMock() {
        firebaseService = mockk<FireBaseService>().also {
            every { it.firstCheck() } returns true
            coEvery { it.signIn(email, password) } returns Unit
            coEvery { it.signUp(email, password) } returns Unit
            coEvery { it.signOut() } returns Unit
            coEvery { it.delete() } returns Unit
        }
    }

    // ログアウト状態設定
    private fun setCallBackException() {
        firebaseService = mockk<FireBaseService>().also {
            every { it.firstCheck() } returns false
            coEvery { it.signIn(email, password) } throws expectedError1
            coEvery { it.signUp(email, password) } throws expectedError2
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
        setCallBackTrueMock()
        initUseCase()

        val result = remoteAccountRepository.autoAuth()

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
        setCallBackException()
        initUseCase()

        val result = remoteAccountRepository.autoAuth()

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
        setCallBackTrueMock()
        initUseCase()

        remoteAccountRepository.signIn(email, password)

        coVerify(exactly = 1) { (firebaseService).signIn(email, password) }
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
        setCallBackException()
        initUseCase()
        runCatching { remoteAccountRepository.signIn(email, password) }
            .onSuccess { fail() }
            .onFailure { assertEquals(expectedError1.message, it.message) }

        coVerify(exactly = 1) { (firebaseService).signIn(email, password) }
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
        setCallBackTrueMock()
        initUseCase()

        remoteAccountRepository.signOut()

        coVerify(exactly = 1) { (firebaseService).signOut() }
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
        setCallBackException()
        initUseCase()
        runCatching { remoteAccountRepository.signOut() }
            .onSuccess { fail() }
            .onFailure { assertEquals(expectedError3.message, it.message) }

        coVerify(exactly = 1) { (firebaseService).signOut() }
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
        val email = Email("123@nr.jp")
        val password = Password("123Abc")
        setCallBackTrueMock()
        initUseCase()

        remoteAccountRepository.signUp(email, password)

        coVerify(exactly = 1) { (firebaseService).signUp(email, password) }
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
        setCallBackException()
        initUseCase()
        runCatching { remoteAccountRepository.signUp(email, password) }
            .onSuccess { fail() }
            .onFailure { assertEquals(expectedError2.message, it.message) }

        coVerify(exactly = 1) { (firebaseService).signUp(email, password) }
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
        setCallBackTrueMock()
        initUseCase()

        remoteAccountRepository.delete()

        coVerify(exactly = 1) { (firebaseService).delete() }
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
        setCallBackException()
        initUseCase()
        runCatching { remoteAccountRepository.delete() }
            .onSuccess { fail() }
            .onFailure { assertEquals(expectedError4.message, it.message) }

        coVerify(exactly = 1) { (firebaseService).delete() }
    }

    // endregion
}