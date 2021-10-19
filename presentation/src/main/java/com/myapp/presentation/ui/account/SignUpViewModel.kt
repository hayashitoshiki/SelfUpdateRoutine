package com.myapp.presentation.ui.account

import com.myapp.presentation.utils.base.BaseAacViewModel
import javax.inject.Inject

/**
 * アカウント作成画面　画面ロジック
 *
 */
class SignUpViewModel @Inject constructor() :
    BaseAacViewModel<SignUpContract.State, SignUpContract.Effect, SignUpContract.Event>() {

    override fun initState(): SignUpContract.State {
        return SignUpContract.State()
    }

    override fun handleEvents(event: SignUpContract.Event) = when(event) {
        is SignUpContract.Event.OnChangeEmail1 -> changeEmail1(event.email)
        is SignUpContract.Event.OnChangeEmail2 -> changeEmail2(event.email)
        is SignUpContract.Event.OnChangePassword1 -> changePassword1(event.password)
        is SignUpContract.Event.OnChangePassword2 -> changePassword2(event.password)
        is SignUpContract.Event.OnClickSignInButton -> signIn()
        is SignUpContract.Event.OnDestroyView -> onDestroyView()
    }

    /**
     * メールアドレス1変更
     *
     * @param email メールアドレス
     */
    private fun changeEmail1(email: String) {
        setState { copy(email1Text = email) }
        changeSignInEnable()
    }

    /**
     * 確認用メールアドレス変更
     *
     * @param email 確認用メールアドレス
     */
    private fun changeEmail2(email: String) {
        setState { copy(email2Text = email) }
        changeSignInEnable()
    }

    /**
     * パスワード変更
     *
     * @param password パスワード
     */
    private fun changePassword1(password: String) {
        setState { copy(password1Text = password) }
        changeSignInEnable()
    }

    /**
     * 確認用パスワード変更
     *
     * @param password パスワード
     */
    private fun changePassword2(password: String) {
        setState { copy(password2Text = password) }
        changeSignInEnable()
    }

    /**
     * ログインボタン活性・非活性変更
     *
     */
    private fun changeSignInEnable() {
        val state = state.value ?: return
        val isSignInEnable =
            state.email1Text.isNotEmpty() && state.email2Text.isNotEmpty() && state.email1Text == state.email2Text
                    && state.password1Text.isNotEmpty() && state.password2Text.isNotEmpty() && state.password1Text == state.password2Text
        setState { copy(isSignInEnable = isSignInEnable) }
    }

    /**
     * ログイン処理
     *
     */
    private fun signIn() {
        // TODO : アカウント作成処理
        setEffect{ SignUpContract.Effect.NavigateHome }
    }

    /**
     * 画面状態初期化
     *
     */
    private fun onDestroyView() {
        cashState = null
        setEffect{ SignUpContract.Effect.OnDestroyView }
    }

}