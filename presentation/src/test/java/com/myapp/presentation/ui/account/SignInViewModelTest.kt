package com.myapp.presentation.ui.account

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.myapp.domain.usecase.AuthUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.*

import org.junit.rules.TestRule

/**
 * ログイン画面　画面ロジック仕様
 *
 */
class SignInViewModelTest {

    private val state = SignInContract.State()
    private lateinit var viewModel: SignInViewModel
    private lateinit var authUseCase: AuthUseCase
    private val expectedError1 = IllegalAccessError("失敗1")

    @ExperimentalCoroutinesApi
    private val coroutineDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    private val testScope = TestCoroutineScope(coroutineDispatcher)

    // LiveData用
    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(coroutineDispatcher)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun initViewModel() {
        viewModel = SignInViewModel(authUseCase)
    }

    // ログイン状態設定
    private fun setStateSignInMock() {
        authUseCase = mockk<AuthUseCase>().also {
            coEvery { it.signIn(any()) } throws expectedError1
        }
    }

    // ログアウト状態設定
    private fun setStateSignOutMock() {
        authUseCase = mockk<AuthUseCase>().also {
            coEvery { it.signIn(any()) } returns Unit
        }
    }

    /**
     * 実行結果比較
     *
     * @param state Stateの期待値
     * @param effect Effectの期待値
     */
    @ExperimentalCoroutinesApi
    private fun result(
        state: SignInContract.State,
        effect: SignInContract.Effect?
    ) = testScope.runBlockingTest {
        val resultState = viewModel.state.value
        val resultEffect: SignInContract.Effect? = viewModel.effect.value

        // 比較
        assertEquals(resultState, state)
        if (resultEffect is SignInContract.Effect.ShowError) {
            val resultMessage = resultEffect.throwable.message
            val message = (effect as SignInContract.Effect.ShowError).throwable.message
            assertEquals(resultMessage, message)
        } else {
            assertEquals(resultEffect, effect)
        }
    }

    // region メールアドレス入力

    /**
     * メールアドレス変更
     *
     * 条件：メールアドレス入力
     * 期待結果；
     * ・画面の値
     * 　　・メールアドレスが入力値になること
     * 　　・ログインボタンが非活性のままであること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onChangeEmailByInput() {
        // 期待値
        val value = "123@com.ne.jp"
        val expectationsEffect = null
        val expectationsState = state.copy(emailText = value,  isSignInEnable = false)

        //実施
        setStateSignOutMock()
        initViewModel()
        viewModel.setEvent( SignInContract.Event.OnChangeEmail(value))

        // 検証
        result(expectationsState, expectationsEffect)
    }

    /**
     * メールアドレス変更
     *
     * 条件：パスワード入力済みの状態でメールアドレス入力
     * 期待結果；
     * ・画面の値
     * 　　・メールアドレスが入力値になること
     * 　　・ログインボタンが活性になること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onChangeEmailByInputAndPassword() {
        // 期待値
        val initPassword = "password"
        val value = "123@com.ne.jp"
        val expectationsEffect = null
        val expectationsState = state.copy(passwordText = initPassword, emailText = value,  isSignInEnable = true)

        //実施
        setStateSignOutMock()
        initViewModel()
        viewModel.setEvent( SignInContract.Event.OnChangePassword(initPassword))
        viewModel.setEvent( SignInContract.Event.OnChangeEmail(value))

        // 検証
        result(expectationsState, expectationsEffect)
    }

    /**
     * メールアドレス変更
     *
     * 条件：メールアドレス・パスワード入力済み、メールアドレス１文字削除
     * 期待結果；
     * ・画面の値
     * 　　・メールアドレスが入力値になること
     * 　　・ログインボタンが活性のままであること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onChangeEmailByDelete() {
        // 期待値
        val initPassword = "password"
        val initEmail = "123@com.ne.jp"
        val value = "123@com.ne.jp"
        val expectationsEffect = null
        val expectationsState = state.copy(passwordText = initPassword, emailText = value,  isSignInEnable = true)

        //実施
        setStateSignOutMock()
        initViewModel()
        viewModel.setEvent( SignInContract.Event.OnChangePassword(initPassword))
        viewModel.setEvent( SignInContract.Event.OnChangeEmail(initEmail))
        viewModel.setEvent( SignInContract.Event.OnChangeEmail(value))

        // 検証
        result(expectationsState, expectationsEffect)
    }

    /**
     * メールアドレス変更
     *
     * 条件：メールアドレス・パスワード入力済みの状態でメールアドレス全削除
     * 期待結果；
     * ・画面の値
     * 　　・メールアドレスが入力値になること
     * 　　・ログインボタンが非活性になること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onChangeEmailByAllDelete() {
        // 期待値
        val initPassword = "password"
        val initEmail = "123@com.ne.jp"
        val value = ""
        val expectationsEffect = null
        val expectationsState = state.copy(passwordText = initPassword, emailText = value,  isSignInEnable = false)

        //実施
        setStateSignOutMock()
        initViewModel()
        viewModel.setEvent( SignInContract.Event.OnChangePassword(initPassword))
        viewModel.setEvent( SignInContract.Event.OnChangeEmail(initEmail))
        viewModel.setEvent( SignInContract.Event.OnChangeEmail(value))

        // 検証
        result(expectationsState, expectationsEffect)
    }

    // endregion

    // region パスワード入力

    /**
     * パスワード変更
     *
     * 条件：パスワードス入力
     * 期待結果；
     * ・画面の値
     * 　　・パスワードが入力値になること
     * 　　・ログインボタンが非活性のままであること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onChangePasswordByInput() {
        // 期待値
        val value = "123@com.ne.jp"
        val expectationsEffect = null
        val expectationsState = state.copy(passwordText = value,  isSignInEnable = false)

        //実施
        setStateSignOutMock()
        initViewModel()
        viewModel.setEvent( SignInContract.Event.OnChangePassword(value))

        // 検証
        result(expectationsState, expectationsEffect)
    }

    /**
     * パスワード変更
     *
     * 条件：メールアドレス入力済みの状態でパスワード入力
     * 期待結果；
     * ・画面の値
     * 　　・パスワードが入力値になること
     * 　　・ログインボタンが活性になること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onChangePasswordByInputAndPassword() {
        // 期待値
        val initEmail = "password"
        val value = "123@com.ne.jp"
        val expectationsEffect = null
        val expectationsState = state.copy(passwordText = value, emailText = initEmail,  isSignInEnable = true)

        //実施
        setStateSignOutMock()
        initViewModel()
        viewModel.setEvent( SignInContract.Event.OnChangePassword(value))
        viewModel.setEvent( SignInContract.Event.OnChangeEmail(initEmail))

        // 検証
        result(expectationsState, expectationsEffect)
    }

    /**
     * パスワード変更
     *
     * 条件：メールアドレス・パスワード入力済みの状態でパスワード１文字削除
     * 期待結果；
     * ・画面の値
     * 　　・メールアドレスが入力値になること
     * 　　・ログインボタンが活性のままであること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onChangePasswordByDelete() {
        // 期待値
        val initPassword = "password"
        val initEmail = "123@com.ne.jp"
        val value = "pass"
        val expectationsEffect = null
        val expectationsState = state.copy(passwordText = value, emailText = initEmail,  isSignInEnable = true)

        //実施
        setStateSignOutMock()
        initViewModel()
        viewModel.setEvent( SignInContract.Event.OnChangePassword(initPassword))
        viewModel.setEvent( SignInContract.Event.OnChangeEmail(initEmail))
        viewModel.setEvent( SignInContract.Event.OnChangePassword(value))

        // 検証
        result(expectationsState, expectationsEffect)
    }

    /**
     * パスワード変更
     *
     * 条件：メールアドレス・パスワード入力済みの状態でパスワード全削除
     * 期待結果；
     * ・画面の値
     * 　　・パスワードが入力値になること
     * 　　・ログインボタンが非活性になること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onChangePasswordByAllDelete() {
        // 期待値
        val initPassword = "password"
        val initEmail = "123@com.ne.jp"
        val value = ""
        val expectationsEffect = null
        val expectationsState = state.copy(passwordText = value, emailText = initEmail,  isSignInEnable = false)

        //実施
        setStateSignOutMock()
        initViewModel()
        viewModel.setEvent( SignInContract.Event.OnChangePassword(initPassword))
        viewModel.setEvent( SignInContract.Event.OnChangeEmail(initEmail))
        viewModel.setEvent( SignInContract.Event.OnChangePassword(value))

        // 検証
        result(expectationsState, expectationsEffect)
    }

    // endregion

    // region ログインボタン押下

    /**
     * ログインボタン押下
     *
     * 条件：ログイン処理が正常に完了
     * 期待結果；
     * ・画面の値
     * 　　・メールアドレスが入力値になること
     * 　　・ログインボタンが活性のままであること
     * ・画面イベント
     * 　　・ホーム画面遷移のイベントが発生すること
     * ・業務ロジック
     * 　　　ログイン処理が走ること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onClickSignInButtonByTrue() {
        // 期待値
        val initPassword = "password"
        val initEmail = "123@com.ne.jp"
        val expectationsEffect = SignInContract.Effect.NavigateHome
        val expectationsState = state.copy(passwordText = initPassword, emailText = initEmail,  isSignInEnable = true)

        //実施
        setStateSignOutMock()
        initViewModel()
        viewModel.setEvent( SignInContract.Event.OnChangePassword(initPassword))
        viewModel.setEvent( SignInContract.Event.OnChangeEmail(initEmail))
        viewModel.setEvent( SignInContract.Event.OnClickSignInButton)

        // 検証
        result(expectationsState, expectationsEffect)
    }

    /**
     * ログインボタン押下
     *
     * 条件：ログイン処理が正常に完了
     * 期待結果；
     * ・画面の値
     * 　　・メールアドレスが入力値になること
     * 　　・ログインボタンが活性のままであること
     * ・画面イベント
     * 　　・Exceptionイベントが発生すること
     * ・業務ロジック
     * 　　　ログイン処理が走ること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onClickSignInButtonByException() {
        // 期待値
        val initPassword = "password"
        val initEmail = "123@com.ne.jp"
        val expectationsEffect = SignInContract.Effect.ShowError(expectedError1)
        val expectationsState = state.copy(passwordText = initPassword, emailText = initEmail,  isSignInEnable = true)

        //実施
        setStateSignInMock()
        initViewModel()
        viewModel.setEvent( SignInContract.Event.OnChangePassword(initPassword))
        viewModel.setEvent( SignInContract.Event.OnChangeEmail(initEmail))
        viewModel.setEvent( SignInContract.Event.OnClickSignInButton)

        // 検証
        result(expectationsState, expectationsEffect)
    }

    // endregion

}