package com.myapp.presentation.ui.account

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.myapp.domain.usecase.AuthUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.rules.TestRule

/**
 * アカウント関連画面 画面ロジック仕様
 *
 */
class AccountViewModelTest {

    private val state = AccountContract.State()
    private lateinit var viewModel: AccountViewModel
    private lateinit var authUseCase: AuthUseCase
    private val expectedError1 = IllegalAccessError("失敗1")
    private val expectedError2 = IllegalAccessError("失敗2")

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
        viewModel = AccountViewModel(authUseCase)
    }

    // ログイン状態設定
    private fun setStateSignInMock() {
        authUseCase = mockk<AuthUseCase>().also {
            every { it.autoAuth() } returns true
            coEvery { it.signOut() } returns Unit
            coEvery { it.delete() } returns Unit
        }
    }

    // ログアウト状態設定
    private fun setStateSignOutMock() {
        authUseCase = mockk<AuthUseCase>().also {
            every { it.autoAuth() } returns false
            coEvery { it.signOut() } throws expectedError1
            coEvery { it.delete() } throws expectedError2
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
        state: AccountContract.State,
        effect: AccountContract.Effect? = null
    ) = testScope.runBlockingTest {
        viewModel.effect
            .stateIn(
                scope = testScope,
                started = SharingStarted.Eagerly,
                initialValue = null
            )
            .onEach { assertEquals(effect, it) }
        assertEquals(state, viewModel.state.value)
    }

    // region ログイン判定

    /**
     * ログイン判定
     *
     * 条件：ログイン状態
     * 期待結果；
     * ・画面の値
     * 　　・ログイン状態がtrueとなること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・業務ロジック
     * 　　　ログイン認証ロジックが走ること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun isSignInByTrue() {
        // 期待値
        val expectationsState = state.copy(isSignIn = true)

        //実施
        setStateSignInMock()
        initViewModel()
        viewModel.setEvent( AccountContract.Event.OnViewCreated)

        // 検証
        result(expectationsState)
        coVerify(exactly = 1) { (authUseCase).autoAuth() }
    }

    /**
     * ログイン判定
     *
     * 条件：未ログイン状態
     * 期待結果；
     * ・画面の値
     * 　　・ログイン状態がfalseとなること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・業務ロジック
     * 　　　ログイン認証ロジックが走ること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun isSignInByFalse() {
        // 期待値
        val expectationsState = state.copy(isSignIn = false)

        //実施
        setStateSignOutMock()
        initViewModel()
        viewModel.setEvent( AccountContract.Event.OnViewCreated)

        // 検証
        result(expectationsState)
        coVerify(exactly = 1) { (authUseCase).autoAuth() }
    }

    // endregion

    // region ログアウト

    /**
     * ログアウト処理
     *
     * 条件：ログイン状態
     * 期待結果；
     * ・画面の値
     * 　　・ログイン状態がfalseとなること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・業務ロジック
     * 　　　ログアウト処理ロジックが走ること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun signOutByTrue() {
        // 期待値
        val expectationsState = state.copy(isSignIn = false)

        //実施
        setStateSignInMock()
        initViewModel()
        viewModel.setEvent(AccountContract.Event.OnViewCreated)
        viewModel.setEvent(AccountContract.Event.OnClickSignOutButton)

        // 検証
        result(expectationsState)
        coVerify(exactly = 1) { (authUseCase).signOut() }
    }

    /**
     * ログアウト処理
     *
     * 条件：未ログイン状態
     * 期待結果；
     * ・画面の値
     * 　　・値が変わらないこと
     * ・画面イベント
     * 　　・Exceptionイベントが走ること
     * ・業務ロジック
     * 　　　ログアウト処理ロジックが走ること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun signOutByFalse() {
        // 期待値
        val expectationsEffect = AccountContract.Effect.ShowError(expectedError1)
        val expectationsState = state.copy(isSignIn = false)

        //実施
        setStateSignOutMock()
        initViewModel()
        viewModel.setEvent(AccountContract.Event.OnViewCreated)
        viewModel.setEvent(AccountContract.Event.OnClickSignOutButton)

        // 検証
        result(expectationsState, expectationsEffect)
        coVerify(exactly = 1) { (authUseCase).signOut() }
    }

    // endregion

    // region アカウント削除

    /**
     * アカウント削除
     *
     * 条件：アカウント削除ボタン押下
     * 期待結果；
     * ・画面の値
     * 　　ー
     * ・画面イベント
     * 　　・アカウント削除確認ダイアログ表示イベント発生すること
     * ・業務ロジック
     * 　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onClickDeleteButton() {
        // 期待値
        val expectationsEffect = AccountContract.Effect.ShorDeleteConfirmDialog
        val expectationsState = state.copy(isSignIn =  true)

        //実施
        setStateSignInMock()
        initViewModel()
        viewModel.setEvent(AccountContract.Event.OnViewCreated)
        viewModel.setEvent(AccountContract.Event.OnClickDeleteButton)

        // 検証
        result(expectationsState, expectationsEffect)
    }

    /**
     * アカウント削除処理
     *
     * 条件：アカウント削除確認画面OKボタン押下_ログイン状態
     * 期待結果；
     * ・画面の値
     * 　　・ログイン状態がfalseとなること
     * ・画面イベント
     * 　　・何もイベントが発生しないこと
     * ・業務ロジック
     * 　　　ログアウト処理ロジックが走ること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun deleteByTrue() {
        // 期待値
        val expectationsState = state.copy(isSignIn = false)

        //実施
        setStateSignInMock()
        initViewModel()
        viewModel.setEvent(AccountContract.Event.OnViewCreated)
        viewModel.setEvent(AccountContract.Event.OnClickDeleteConfirmOkButton)

        // 検証
        result(expectationsState)
        coVerify(exactly = 1) { (authUseCase).delete() }
    }

    /**
     * アカウント削除処理
     *
     * 条件：アカウント削除確認画面OKボタン押下_未ログイン状態
     * 期待結果；
     * ・画面の値
     * 　　・値が変わらないこと
     * ・画面イベント
     * 　　・Exceptionイベントが走ること
     * ・業務ロジック
     * 　　　アカウント削除処理ロジックが走ること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun deleteByFalse() {
        // 期待値
        val expectationsEffect = AccountContract.Effect.ShowError(expectedError2)
        val expectationsState = state.copy(isSignIn = false)

        //実施
        setStateSignOutMock()
        initViewModel()
        viewModel.setEvent(AccountContract.Event.OnViewCreated)
        viewModel.setEvent(AccountContract.Event.OnClickDeleteConfirmOkButton)

        // 検証
        result(expectationsState, expectationsEffect)
        coVerify(exactly = 1) { (authUseCase).delete() }
    }

    // endregion


    // region ログインボタン

    /**
     * ログインボタン押下
     *
     * 条件：ログイン状態
     * 期待結果；
     * ・画面の値
     * 　　何も変化しないこと
     * ・画面イベント
     * 　　・ログイン画面遷移イベントが走ること
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onClickSignIn() {
        // 期待値
        val expectationsEffect = AccountContract.Effect.NavigateSignIn
        val expectationsState = state

        //実施
        setStateSignInMock()
        initViewModel()
        viewModel.setEvent(AccountContract.Event.OnClickSignInButton)

        // 検証
        result(expectationsState, expectationsEffect)
    }

    // endregion

    // region アカウント作成ボタン

    /**
     * アカウント作成ボタン押下
     *
     * 条件：ログイン状態
     * 期待結果；
     * ・画面の値
     * 　　何も変化しないこと
     * ・画面イベント
     * 　　・アカウント作成画面遷移イベントが走ること
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onClickSignUp() {
        // 期待値
        val expectationsEffect = AccountContract.Effect.NavigateSignUp
        val expectationsState = state

        //実施
        setStateSignInMock()
        initViewModel()
        viewModel.setEvent(AccountContract.Event.OnClickSignUpButton)

        // 検証
        result(expectationsState, expectationsEffect)
    }

    // endregion

}