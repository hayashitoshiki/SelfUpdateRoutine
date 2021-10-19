package com.myapp.presentation.ui.account

import com.myapp.presentation.utils.base.BaseAacViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ログイン画面　画面ロジック
 *
 */
@HiltViewModel
class SignInViewModel @Inject constructor() :
    BaseAacViewModel<SignInContract.State, SignInContract.Effect, SignInContract.Event>() {

    override fun initState(): SignInContract.State {
        return SignInContract.State()
    }

    override fun handleEvents(event: SignInContract.Event) = when(event) {
        is SignInContract.Event.OnChangeEmail -> changeEmail(event.email)
        is SignInContract.Event.OnChangePassword -> changePassword(event.password)
        is SignInContract.Event.OnClickSignInButton -> signIn()
        is SignInContract.Event.OnDestroyView -> onDestroyView()
    }

    /**
     * メールアドレス変更
     *
     * @param email メールアドレス
     */
    private fun changeEmail(email: String) {
        setState { copy(emailText = email) }
        changeSignInEnable()
    }

    /**
     * パスワード変更
     *
     * @param password パスワード
     */
    private fun changePassword(password: String) {
        setState { copy(passwordText = password) }
        changeSignInEnable()
    }

    /**
     * ログインボタン活性・非活性変更
     *
     */
    private fun changeSignInEnable() {
        val state = state.value ?: return
        val isSignInEnable = state.emailText.isNotEmpty() && state.passwordText.isNotEmpty()
        setState { copy(isSignInEnable = isSignInEnable) }
    }

    /**
     * ログイン処理
     *
     */
    private fun signIn() {
        // TODO : ログイン処理
        setEffect{ SignInContract.Effect.NavigateHome }
    }

    /**
     * 画面状態初期化
     *
     */
    private fun onDestroyView() {
        cashState = null
        setEffect{ SignInContract.Effect.OnDestroyView }
    }

}