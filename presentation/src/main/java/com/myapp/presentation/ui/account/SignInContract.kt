package com.myapp.presentation.ui.account

import com.myapp.presentation.utils.base.BaseContract

interface SignInContract {

    /**
     * アカウント認証画面 状態保持
     *
     * @property emailText メールアドレス
     * @property passwordText パスワード
     * @property isSignInEnable ログインボタン活性・非活性
     */
    data class State(
        val emailText: String = "",
        val passwordText: String = "",
        val isSignInEnable: Boolean = false
    ) : BaseContract.State

    /**
     * UIイベント
     *
     */
    sealed class Effect : BaseContract.Effect {

        /**
         * ログインボタン押下
         */
        object NavigateHome : Effect()

        /**
         * ProgressBarの表示制御
         *
         * @property value 表示切り替え
         */
        data class ShorProgressBer(val value: Boolean) : Effect()

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
         * @property email メールアドレス
         */
        data class OnChangeEmail(val email: String) : Event()

        /**
         * パスワード変更
         * @property password パスワード
         */
        data class OnChangePassword(val password: String) : Event()

        /**
         * ログインボタン押下
         */
        object OnClickSignInButton : Event()

        /**
         * ライフサイクル(onDestroyView)
         */
        object OnDestroyView : Event()
    }
}