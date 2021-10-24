package com.myapp.presentation.ui.account

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.myapp.domain.dto.AuthInputDto
import com.myapp.domain.model.value.Email
import com.myapp.domain.model.value.Password
import com.myapp.domain.usecase.AuthUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.*

import org.junit.rules.TestRule

/**
 * アカウント登録画面　画面ロジック仕様
 *
 */
class SignUpViewModelTest {

    @ExperimentalCoroutinesApi
    private val coroutineDispatcher = TestCoroutineDispatcher()

    @ExperimentalCoroutinesApi
    private val testScope = TestCoroutineScope(coroutineDispatcher)

    @Rule
    @JvmField
    val rule: TestRule = InstantTaskExecutorRule()

    private val state = SignUpContract.State()
    private lateinit var viewModel: SignUpViewModel
    private lateinit var authUseCase: AuthUseCase
    private val expectedError1 = IllegalAccessError("失敗1")

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(coroutineDispatcher)
        authUseCase = mockk<AuthUseCase>().also {
            coEvery { it.signUp(any()) } returns Unit
        }
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        coroutineDispatcher.cleanupTestCoroutines()
    }

    private fun setSignUpTrue() {
        authUseCase = mockk<AuthUseCase>().also {
            coEvery { it.signUp(any()) } returns Unit
        }
    }

    private fun setSignUpException() {
        authUseCase = mockk<AuthUseCase>().also {
            coEvery { it.signUp(any()) } throws expectedError1
        }
    }

    /**
     * 実行結果比較
     *
     * @param state Stateの期待値
     * @param effect Effectの期待値
     */
    @ExperimentalCoroutinesApi
    private fun result(state: SignUpContract.State, effect: SignUpContract.Effect?) = testScope.runBlockingTest {
        val resultState = viewModel.state.value
        val resultEffect = viewModel.effect.value

        // 比較
        assertEquals(resultState, state)
        if (resultEffect is SignUpContract.Effect.ShowError) {
            val resultMessage = resultEffect.throwable.message
            val message = (effect as SignUpContract.Effect.ShowError).throwable.message
            assertEquals(resultMessage, message)
        } else {
            assertEquals(resultEffect, effect)
        }
    }

    // region メールアドレス１変更

    /**
     * メールアドレス１変更
     *
     * 条件：なし
     * 期待結果；
     * ・画面の値
     * 　　・メールアドレス１が入力した値になること
     * ・画面イベント
     * 　　　ー
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeEmail1() = testScope.runBlockingTest {

        // 期待結果
        val value = "123@com.ne.jp"
        val expectationsEffect = null
        val expectationsState = state.copy(email1Text= value)

        // 実施
        viewModel = SignUpViewModel(authUseCase)
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail1(value))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    // endregion

    // region メールアドレス２変更

    /**
     * 確認用メールアドレス変更
     *
     * 条件：なし
     * 期待結果；
     * ・画面の値
     * 　　・確認用メールアドレスが入力した値になること
     * ・画面イベント
     * 　　　ー
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changeEmail2() = testScope.runBlockingTest {

        // 期待結果
        val value = "123@com.ne.jp"
        val expectationsEffect = null
        val expectationsState = state.copy(email2Text = value)

        // 実施
        viewModel = SignUpViewModel(authUseCase)
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail2(value))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    // endregion

    // region パスワード1変更

    /**
     * パスワード変更
     *
     * 条件：なし
     * 期待結果；
     * ・画面の値
     * 　　・パスワードが入力した値になること
     * ・画面イベント
     * 　　　ー
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changePassword1() = testScope.runBlockingTest {

        // 期待結果
        val value = "123Abc"
        val expectationsEffect = null
        val expectationsState = state.copy(password1Text = value)

        // 実施
        viewModel = SignUpViewModel(authUseCase)
        viewModel.setEvent(SignUpContract.Event.OnChangePassword1(value))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    // endregion

    // region パスワード２変更

    /**
     * 確認用パスワード変更
     *
     * 条件：なし
     * 期待結果；
     * ・画面の値
     * 　　・確認用パスワードが入力した値になること
     * ・画面イベント
     * 　　　ー
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun changePassword2() = testScope.runBlockingTest {

        // 期待結果
        val value = "123Abc"
        val expectationsEffect = null
        val expectationsState = state.copy(password2Text = value)

        // 実施
        viewModel = SignUpViewModel(authUseCase)
        viewModel.setEvent(SignUpContract.Event.OnChangePassword2(value))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    // endregion

    // region バリデーション

    /**
     * バリデーション
     *
     * 条件：メールアドレスとパスワードが正常系
     * 期待結果；
     * ・画面の値
     * 　　・メールアドレスが入力した値になること
     * 　　・パスワードが入力した値になること
     * 　　・登録ボタンが活性状態（true）になること
     * ・画面イベント
     * 　　　ー
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun validateByOk() = testScope.runBlockingTest {

        // 期待結果
        val email = "123@com.ne.jp"
        val password = "123Abc"
        val expectationsEffect = null
        val expectationsState = state.copy(
            email1Text= email,
            email2Text = email,
            password1Text = password,
            password2Text = password,
            isSignUpEnable= true
        )

        // 実施
        viewModel = SignUpViewModel(authUseCase)
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail1(email))
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail2(email))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword1(password))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword2(password))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    // region メールアドレス変更

    /**
     * バリデーション
     *
     * 条件：メールアドレスが不一致
     * 期待結果；
     * ・画面の値
     * 　　・メールアドレスが入力した値になること
     * 　　・パスワードが入力した値になること
     * 　　・登録ボタンが活性状態（true）になること
     * ・画面イベント
     * 　　　ー
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun validateByEmailNotEqual() = testScope.runBlockingTest {

        // 期待結果
        val email1 = "123@com.ne.jp"
        val email2 = "124@com.ne.jp"
        val password = "123Abc"
        val expectationsEffect = null
        val expectationsState = state.copy(
            email1Text= email1,
            email2Text = email2,
            password1Text = password,
            password2Text = password,
            isSignUpEnable= false
        )

        // 実施
        viewModel = SignUpViewModel(authUseCase)
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail1(email1))
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail2(email2))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword1(password))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword2(password))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * バリデーション
     *
     * 条件：メールアドレスがアカウント部分のみ
     * 期待結果；
     * ・画面の値
     * 　　・メールアドレス１が入力した値になること
     * 　　・登録ボタンが非活性状態（false）になること
     * ・画面イベント
     * 　　　ー
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun validateByEmailOnlyAccount() = testScope.runBlockingTest {

        // 期待結果
        val email = "123"
        val password = "123Abc"
        val expectationsEffect = null
        val expectationsState = state.copy(
            email1Text= email,
            email2Text = email,
            password1Text = password,
            password2Text = password,
            isSignUpEnable= false
        )

        // 実施
        viewModel = SignUpViewModel(authUseCase)
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail1(email))
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail2(email))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword1(password))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword2(password))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * バリデーション
     *
     * 条件：メールアドレスが@のみ
     * 期待結果；
     * ・画面の値
     * 　　・メールアドレス１が入力した値になること
     * 　　・登録ボタンが非活性状態（false）になること
     * ・画面イベント
     * 　　　ー
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun validateByEmailOnlyAtSign() = testScope.runBlockingTest {

        // 期待結果
        val email = "@"
        val password = "123Abc"
        val expectationsEffect = null
        val expectationsState = state.copy(
            email1Text= email,
            email2Text = email,
            password1Text = password,
            password2Text = password,
            isSignUpEnable= false
        )

        // 実施
        viewModel = SignUpViewModel(authUseCase)
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail1(email))
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail2(email))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword1(password))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword2(password))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * バリデーション
     *
     * 条件：メールアドレスがドメインのみ
     * 期待結果；
     * ・画面の値
     * 　　・メールアドレス１が入力した値になること
     * 　　・登録ボタンが非活性状態（false）になること
     * ・画面イベント
     * 　　　ー
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun validateByEmailOnlyDomain() = testScope.runBlockingTest {

        // 期待結果
        val email = "ezweb.ne.jp"
        val password = "123Abc"
        val expectationsEffect = null
        val expectationsState = state.copy(
            email1Text= email,
            email2Text = email,
            password1Text = password,
            password2Text = password,
            isSignUpEnable= false
        )

        // 実施
        viewModel = SignUpViewModel(authUseCase)
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail1(email))
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail2(email))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword1(password))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword2(password))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * バリデーション
     *
     * 条件：メールアドレスが@以降のみ
     * 期待結果；
     * ・画面の値
     * 　　・メールアドレス１が入力した値になること
     * 　　・登録ボタンが非活性状態（false）になること
     * ・画面イベント
     * 　　　ー
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun validateByEmailOnlyAfterAtSign() = testScope.runBlockingTest {

        // 期待結果
        val email = "@ezweb.ne.jp"
        val password = "123Abc"
        val expectationsEffect = null
        val expectationsState = state.copy(
            email1Text= email,
            email2Text = email,
            password1Text = password,
            password2Text = password,
            isSignUpEnable= false
        )

        // 実施
        viewModel = SignUpViewModel(authUseCase)
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail1(email))
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail2(email))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword1(password))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword2(password))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * バリデーション
     *
     * 条件：メールアドレスが@までのみ
     * 期待結果；
     * ・画面の値
     * 　　・メールアドレス１が入力した値になること
     * 　　・登録ボタンが非活性状態（false）になること
     * ・画面イベント
     * 　　　ー
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun validateByEmailOnlyBeforeAtSign() = testScope.runBlockingTest {

        // 期待結果
        val email = "123@"
        val password = "123Abc"
        val expectationsEffect = null
        val expectationsState = state.copy(
            email1Text= email,
            email2Text = email,
            password1Text = password,
            password2Text = password,
            isSignUpEnable= false
        )

        // 実施
        viewModel = SignUpViewModel(authUseCase)
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail1(email))
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail2(email))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword1(password))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword2(password))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * バリデーション
     *
     * 条件：メールアドレスのドメインが第１度メインのみ
     * 期待結果；
     * ・画面の値
     * 　　・メールアドレス１が入力した値になること
     * 　　・登録ボタンが非活性状態（false）になること
     * ・画面イベント
     * 　　　ー
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun validateByEmailDomainFirst() = testScope.runBlockingTest {

        // 期待結果
        val email = "123@com"
        val password = "123Abc"
        val expectationsEffect = null
        val expectationsState = state.copy(
            email1Text= email,
            email2Text = email,
            password1Text = password,
            password2Text = password,
            isSignUpEnable= false
        )

        // 実施
        viewModel = SignUpViewModel(authUseCase)
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail1(email))
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail2(email))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword1(password))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword2(password))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * バリデーション
     *
     * 条件：メールアドレスのPC用メールアドレス
     * 期待結果；
     * ・画面の値
     * 　　・メールアドレス１が入力した値になること
     * 　　・登録ボタンが非活性状態（true）になること
     * ・画面イベント
     * 　　　ー
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun validateByEmailDomainPc() = testScope.runBlockingTest {

        // 期待結果
        val email = "123@com.jp"
        val password = "123Abc"
        val expectationsEffect = null
        val expectationsState = state.copy(
            email1Text= email,
            email2Text = email,
            password1Text = password,
            password2Text = password,
            isSignUpEnable= true
        )

        // 実施
        viewModel = SignUpViewModel(authUseCase)
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail1(email))
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail2(email))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword1(password))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword2(password))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * バリデーション
     *
     * 条件：メールアドレス携帯用メールアドレス
     * 期待結果；
     * ・画面の値
     * 　　・メールアドレス１が入力した値になること
     * 　　・登録ボタンが非活性状態（true）になること
     * ・画面イベント
     * 　　　ー
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun validateByEmailDomainPhone() = testScope.runBlockingTest {

        // 期待結果
        val email = "123@ezweb.ne.jp"
        val password = "123Abc"
        val expectationsEffect = null
        val expectationsState = state.copy(
            email1Text= email,
            email2Text = email,
            password1Text = password,
            password2Text = password,
            isSignUpEnable= true
        )

        // 実施
        viewModel = SignUpViewModel(authUseCase)
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail1(email))
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail2(email))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword1(password))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword2(password))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * バリデーション
     *
     * 条件：メールアドレス１を正常から異常へ変更
     * 期待結果；
     * ・画面の値
     * 　　・メールアドレスが入力した値になること
     * 　　・パスワードが入力した値になること
     * 　　・登録ボタンが非活性状態（false）になること
     * ・画面イベント
     * 　　　ー
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun validateByEmailChangeEmail1False() = testScope.runBlockingTest {

        // 期待結果
        val email = "123@com.ne.jp"
        val after = "123@com"
        val password = "123Abc"
        val expectationsEffect = null
        val expectationsState = state.copy(
            email1Text= after,
            email2Text = email,
            password1Text = password,
            password2Text = password,
            isSignUpEnable= false
        )

        // 実施
        viewModel = SignUpViewModel(authUseCase)
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail1(email))
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail2(email))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword1(password))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword2(password))
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail1(after))

        // 比較
        result(expectationsState, expectationsEffect)
    }


    /**
     * バリデーション
     *
     * 条件：メールアドレス１を異常から正常へ変更
     * 期待結果；
     * ・画面の値
     * 　　・メールアドレスが入力した値になること
     * 　　・パスワードが入力した値になること
     * 　　・登録ボタンが非活性状態（true）になること
     * ・画面イベント
     * 　　　ー
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun validateByEmailChangeEmail1True() = testScope.runBlockingTest {

        // 期待結果
        val email = "123@com"
        val after = "123@com.ne.jp"
        val password = "123Abc"
        val expectationsEffect = null
        val expectationsState = state.copy(
            email1Text= after,
            email2Text = after,
            password1Text = password,
            password2Text = password,
            isSignUpEnable= true
        )

        // 実施
        viewModel = SignUpViewModel(authUseCase)
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail1(email))
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail2(after))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword1(password))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword2(password))
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail1(after))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * バリデーション
     *
     * 条件：確認用メールアドレスを正常から異常へ変更
     * 期待結果；
     * ・画面の値
     * 　　・メールアドレスが入力した値になること
     * 　　・パスワードが入力した値になること
     * 　　・登録ボタンが非活性状態（false）になること
     * ・画面イベント
     * 　　　ー
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun validateByEmailChangeEmail2False() = testScope.runBlockingTest {

        // 期待結果
        val email = "123@com.ne.jp"
        val after = "123@com"
        val password = "123Abc"
        val expectationsEffect = null
        val expectationsState = state.copy(
            email1Text= email,
            email2Text = after,
            password1Text = password,
            password2Text = password,
            isSignUpEnable= false
        )

        // 実施
        viewModel = SignUpViewModel(authUseCase)
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail1(email))
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail2(email))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword1(password))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword2(password))
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail2(after))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * バリデーション
     *
     * 条件：確認用メールアドレスを異常から正常へ変更
     * 期待結果；
     * ・画面の値
     * 　　・メールアドレスが入力した値になること
     * 　　・パスワードが入力した値になること
     * 　　・登録ボタンが非活性状態（true）になること
     * ・画面イベント
     * 　　　ー
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun validateByEmailChangeEmail2True() = testScope.runBlockingTest {

        // 期待結果
        val email = "123@com"
        val after = "123@com.ne.jp"
        val password = "123Abc"
        val expectationsEffect = null
        val expectationsState = state.copy(
            email1Text= after,
            email2Text = after,
            password1Text = password,
            password2Text = password,
            isSignUpEnable= true
        )

        // 実施
        viewModel = SignUpViewModel(authUseCase)
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail1(after))
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail2(email))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword1(password))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword2(password))
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail2(after))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    // endregion

    // region パスワード変更

    /**
     * バリデーション
     *
     * 条件：パスワードが不一致
     * 期待結果；
     * ・画面の値
     * 　　・メールアドレスが入力した値になること
     * 　　・パスワードが入力した値になること
     * 　　・登録ボタンが非活性状態（false）になること
     * ・画面イベント
     * 　　　ー
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun validateByPasswordNotEqual() = testScope.runBlockingTest {

        // 期待結果
        val email = "123@com.ne.jp"
        val password1 = "123Abc"
        val password2 = "123Ab"
        val expectationsEffect = null
        val expectationsState = state.copy(
            email1Text= email,
            email2Text = email,
            password1Text = password1,
            password2Text = password2,
            isSignUpEnable= false
        )

        // 実施
        viewModel = SignUpViewModel(authUseCase)
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail1(email))
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail2(email))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword1(password1))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword2(password2))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * バリデーション
     *
     * 条件：パスワードが６桁未満
     * 期待結果；
     * ・画面の値
     * 　　・メールアドレスが入力した値になること
     * 　　・パスワードが入力した値になること
     * 　　・登録ボタンが非活性状態（false）になること
     * ・画面イベント
     * 　　　ー
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun validateByPasswordNotLength() = testScope.runBlockingTest {

        // 期待結果
        val email = "123@com.ne.jp"
        val password = "123Ab"
        val expectationsEffect = null
        val expectationsState = state.copy(
            email1Text= email,
            email2Text = email,
            password1Text = password,
            password2Text = password,
            isSignUpEnable= false
        )

        // 実施
        viewModel = SignUpViewModel(authUseCase)
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail1(email))
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail2(email))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword1(password))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword2(password))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * バリデーション
     *
     * 条件：パスワードが数値のみ
     * 期待結果；
     * ・画面の値
     * 　　・メールアドレスが入力した値になること
     * 　　・パスワードが入力した値になること
     * 　　・登録ボタンが非活性状態（false）になること
     * ・画面イベント
     * 　　　ー
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun validateByPasswordOnlyNumber() = testScope.runBlockingTest {

        // 期待結果
        val email = "123@com.ne.jp"
        val password = "123456"
        val expectationsEffect = null
        val expectationsState = state.copy(
            email1Text= email,
            email2Text = email,
            password1Text = password,
            password2Text = password,
            isSignUpEnable= false
        )

        // 実施
        viewModel = SignUpViewModel(authUseCase)
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail1(email))
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail2(email))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword1(password))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword2(password))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * バリデーション
     *
     * 条件：パスワードが小文字の英文字のみ
     * 期待結果；
     * ・画面の値
     * 　　・メールアドレスが入力した値になること
     * 　　・パスワードが入力した値になること
     * 　　・登録ボタンが非活性状態（false）になること
     * ・画面イベント
     * 　　　ー
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun validateByPasswordOnlySmallAlphabet() = testScope.runBlockingTest {

        // 期待結果
        val email = "123@com.ne.jp"
        val password = "password"
        val expectationsEffect = null
        val expectationsState = state.copy(
            email1Text= email,
            email2Text = email,
            password1Text = password,
            password2Text = password,
            isSignUpEnable= false
        )

        // 実施
        viewModel = SignUpViewModel(authUseCase)
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail1(email))
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail2(email))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword1(password))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword2(password))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * バリデーション
     *
     * 条件：パスワードが大文字の英文字のみ
     * 期待結果；
     * ・画面の値
     * 　　・メールアドレスが入力した値になること
     * 　　・パスワードが入力した値になること
     * 　　・登録ボタンが非活性状態（false）になること
     * ・画面イベント
     * 　　　ー
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun validateByPasswordOnlyBigAlphabet() = testScope.runBlockingTest {

        // 期待結果
        val email = "123@com.ne.jp"
        val password = "PASSWORD"
        val expectationsEffect = null
        val expectationsState = state.copy(
            email1Text= email,
            email2Text = email,
            password1Text = password,
            password2Text = password,
            isSignUpEnable= false
        )

        // 実施
        viewModel = SignUpViewModel(authUseCase)
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail1(email))
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail2(email))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword1(password))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword2(password))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * バリデーション
     *
     * 条件：パスワードが大文字と小文字の英文字のみ
     * 期待結果；
     * ・画面の値
     * 　　・メールアドレスが入力した値になること
     * 　　・パスワードが入力した値になること
     * 　　・登録ボタンが非活性状態（false）になること
     * ・画面イベント
     * 　　　ー
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun validateByPasswordOnlySmallAndBigAlphabet() = testScope.runBlockingTest {

        // 期待結果
        val email = "123@com.ne.jp"
        val password = "PassWord"
        val expectationsEffect = null
        val expectationsState = state.copy(
            email1Text= email,
            email2Text = email,
            password1Text = password,
            password2Text = password,
            isSignUpEnable= false
        )

        // 実施
        viewModel = SignUpViewModel(authUseCase)
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail1(email))
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail2(email))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword1(password))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword2(password))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * バリデーション
     *
     * 条件：パスワードが数値と大文字の英文字のみ
     * 期待結果；
     * ・画面の値
     * 　　・メールアドレスが入力した値になること
     * 　　・パスワードが入力した値になること
     * 　　・登録ボタンが非活性状態（false）になること
     * ・画面イベント
     * 　　　ー
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun validateByPasswordOnlyNumberAndBigAlphabet() = testScope.runBlockingTest {

        // 期待結果
        val email = "123@com.ne.jp"
        val password = "123PASS"
        val expectationsEffect = null
        val expectationsState = state.copy(
            email1Text= email,
            email2Text = email,
            password1Text = password,
            password2Text = password,
            isSignUpEnable= false
        )

        // 実施
        viewModel = SignUpViewModel(authUseCase)
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail1(email))
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail2(email))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword1(password))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword2(password))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * バリデーション
     *
     * 条件：パスワードが数値と小文字の英文字のみ
     * 期待結果；
     * ・画面の値
     * 　　・メールアドレスが入力した値になること
     * 　　・パスワードが入力した値になること
     * 　　・登録ボタンが非活性状態（false）になること
     * ・画面イベント
     * 　　　ー
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun validateByPasswordOnlyNumberAndSmallAlphabet() = testScope.runBlockingTest {

        // 期待結果
        val email = "123@com.ne.jp"
        val password = "123pass"
        val expectationsEffect = null
        val expectationsState = state.copy(
            email1Text= email,
            email2Text = email,
            password1Text = password,
            password2Text = password,
            isSignUpEnable= false
        )

        // 実施
        viewModel = SignUpViewModel(authUseCase)
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail1(email))
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail2(email))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword1(password))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword2(password))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * バリデーション
     *
     * 条件：パスワード１を正常から異常へ変更
     * 期待結果；
     * ・画面の値
     * 　　・メールアドレスが入力した値になること
     * 　　・パスワードが入力した値になること
     * 　　・登録ボタンが非活性状態（false）になること
     * ・画面イベント
     * 　　　ー
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun validateByPasswordChangePassword1False() = testScope.runBlockingTest {

        // 期待結果
        val email = "123@com.ne.jp"
        val password = "123Pass"
        val after = "123pass"
        val expectationsEffect = null
        val expectationsState = state.copy(
            email1Text= email,
            email2Text = email,
            password1Text = after,
            password2Text = password,
            isSignUpEnable= false
        )

        // 実施
        viewModel = SignUpViewModel(authUseCase)
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail1(email))
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail2(email))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword1(password))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword2(password))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword1(after))

        // 比較
        result(expectationsState, expectationsEffect)
    }
    /**
     * バリデーション
     *
     * 条件：パスワード１を異常から正常へ変更
     * 期待結果；
     * ・画面の値
     * 　　・メールアドレスが入力した値になること
     * 　　・パスワードが入力した値になること
     * 　　・登録ボタンが非活性状態（false）になること
     * ・画面イベント
     * 　　　ー
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun validateByPasswordChangePassword1True() = testScope.runBlockingTest {

        // 期待結果
        val email = "123@com.ne.jp"
        val password = "123pass"
        val after = "123Pass"
        val expectationsEffect = null
        val expectationsState = state.copy(
            email1Text= email,
            email2Text = email,
            password1Text = after,
            password2Text = after,
            isSignUpEnable= true
        )

        // 実施
        viewModel = SignUpViewModel(authUseCase)
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail1(email))
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail2(email))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword1(password))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword2(after))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword1(after))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * バリデーション
     *
     * 条件：確認用パスワードを正常から異常へ変更
     * 期待結果；
     * ・画面の値
     * 　　・メールアドレスが入力した値になること
     * 　　・パスワードが入力した値になること
     * 　　・登録ボタンが非活性状態（false）になること
     * ・画面イベント
     * 　　　ー
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun validateByPasswordChangePassword2False() = testScope.runBlockingTest {

        // 期待結果
        val email = "123@com.ne.jp"
        val password = "123Pass"
        val after = "123pass"
        val expectationsEffect = null
        val expectationsState = state.copy(
            email1Text= email,
            email2Text = email,
            password1Text = password,
            password2Text = after,
            isSignUpEnable= false
        )

        // 実施
        viewModel = SignUpViewModel(authUseCase)
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail1(email))
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail2(email))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword1(password))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword2(password))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword2(after))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    /**
     * バリデーション
     *
     * 条件：確認用パスワードを異常から正常へ変更
     * 期待結果；
     * ・画面の値
     * 　　・メールアドレスが入力した値になること
     * 　　・パスワードが入力した値になること
     * 　　・登録ボタンが非活性状態（false）になること
     * ・画面イベント
     * 　　　ー
     * ・業務ロジック
     * 　　　ー
     */
    @ExperimentalCoroutinesApi
    @Test
    fun validateByPasswordChangePassword2True() = testScope.runBlockingTest {

        // 期待結果
        val email = "123@com.ne.jp"
        val password = "123pass"
        val after = "123Pass"
        val expectationsEffect = null
        val expectationsState = state.copy(
            email1Text= email,
            email2Text = email,
            password1Text = after,
            password2Text = after,
            isSignUpEnable= true
        )

        // 実施
        viewModel = SignUpViewModel(authUseCase)
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail1(email))
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail2(email))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword1(after))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword2(password))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword2(after))

        // 比較
        result(expectationsState, expectationsEffect)
    }

    // endregion

    // endregion

    // region 登録ボタン押下

    /**
     * 登録ボタン押下
     *
     * 条件：新規登録処理が正常に返る
     * 期待結果；
     * ・画面の値
     * 　　　ー
     * ・画面イベント
     * 　　　ホーム画面遷移のイベントが発火されること
     * ・業務ロジック
     * 　　　新規登録処理が呼ばれること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onClickSignUpByTrue() = testScope.runBlockingTest {

        // 期待結果
        val email = "123@com.ne.jp"
        val password = "123Password"
        val dto = AuthInputDto(Email(email), Password(password))
        val expectationsEffect = SignUpContract.Effect.NavigateHome
        val expectationsState = state.copy(
            email1Text= email,
            email2Text = email,
            password1Text = password,
            password2Text = password,
            isSignUpEnable= true
        )

        // 実施
        setSignUpTrue()
        viewModel = SignUpViewModel(authUseCase)
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail1(email))
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail2(email))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword1(password))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword2(password))
        viewModel.setEvent(SignUpContract.Event.OnClickSignUpButton)

        // 比較
        result(expectationsState, expectationsEffect)
        coVerify(exactly = 1) { (authUseCase).signUp(dto) }
    }

    /**
     * 登録ボタン押下
     *
     * 条件：新規登録処理が異常で返る
     * 期待結果；
     * ・画面の値
     * 　　　ー
     * ・画面イベント
     * 　　　エラー処理のイベントが発火されること
     * ・業務ロジック
     * 　　　新規登録処理が呼ばれること
     */
    @ExperimentalCoroutinesApi
    @Test
    fun onClickSignUpByFalse() = testScope.runBlockingTest {

        // 期待結果
        val email = "123@com.ne.jp"
        val password = "123Password"
        val dto = AuthInputDto(Email(email), Password(password))
        val expectationsEffect = SignUpContract.Effect.ShowError(expectedError1)
        val expectationsState = state.copy(
            email1Text= email,
            email2Text = email,
            password1Text = password,
            password2Text = password,
            isSignUpEnable= true
        )

        // 実施
        setSignUpException()
        viewModel = SignUpViewModel(authUseCase)
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail1(email))
        viewModel.setEvent(SignUpContract.Event.OnChangeEmail2(email))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword1(password))
        viewModel.setEvent(SignUpContract.Event.OnChangePassword2(password))
       viewModel.setEvent(SignUpContract.Event.OnClickSignUpButton)

        // 比較
        result(expectationsState, expectationsEffect)
        coVerify(exactly = 1) { (authUseCase).signUp(dto) }
    }

    // endregion
}