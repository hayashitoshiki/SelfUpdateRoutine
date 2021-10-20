package com.myapp.presentation.ui.account

import com.myapp.presentation.utils.base.BaseContract

interface SignUpContract {

    /**
     * アカウント認証画面 状態保持
     *
     * @property email1Text メールアドレス
     * @property email2Text メールアドレス
     * @property password1Text パスワード
     * @property password2Text パスワード
     * @property isSignUpEnable ログインボタン活性・非活性
     */
    data class State(
        val email1Text: String = "",
        val email2Text: String = "",
        val password1Text: String = "",
        val password2Text: String = "",
        val isSignUpEnable: Boolean = false
    ) : BaseContract.State

    /**
     * UIイベント
     *
     */
    sealed class Effect : BaseContract.Effect {

        /**
         * ホーム画面遷移
         */
        object NavigateHome : Effect()

        /**
         * エラー表示
         */
        data class ShowError(val throwable: Throwable) : Effect()

        /**
         * ライフサイクル(onDestroyView)
         */
        object OnDestroyView : Effect()
    }

    /**
     * アクション
     *
     */
    sealed class Event : BaseContract.Event {

        /**
         * メールアドレス変更
         */
        data class OnChangeEmail1(val email: String) : Event()

        /**
         * メールアドレス変更
         */
        data class OnChangeEmail2(val email: String) : Event()

        /**
         * パスワード変更
         */
        data class OnChangePassword1(val password: String) : Event()

        /**
         * パスワード変更
         */
        data class OnChangePassword2(val password: String) : Event()

        /**
         * アカウント作成ボタン押下
         */
        object OnClickSignInButton : Event()

        /**
         * ライフサイクル(onDestroyView)
         */
        object OnDestroyView : Event()
    }
}