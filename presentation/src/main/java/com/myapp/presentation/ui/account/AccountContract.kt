package com.myapp.presentation.ui.account

import com.myapp.presentation.utils.base.BaseContract

interface AccountContract {

    /**
     * アカウント認証画面 状態保持
     *
     * @property isSignIn ログイン済みチェック
     */
    data class State(
        val isSignIn: Boolean = false
    ) : BaseContract.State

    /**
     * UIイベント
     *
     */
    sealed class Effect : BaseContract.Effect {
        /**
         * アカウント作成ボタン押下
         */
        object NavigateSignUp : Effect()

        /**
         * ログインボタン押下
         */
        object NavigateSignIn : Effect()

        /**
         * ログアウトボタン押下
         */
        object NavigateSignOut : Effect()

        /**
         * アカウント削除ボタン押下
         */
        object NavigateDelete : Effect()

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
         * アカウント作成ボタン押下
         */
        object OnClickSignUpButton : Event()

        /**
         * ログインボタン押下
         */
        object OnClickSignInButton : Event()

        /**
         * ログアウトボタン押下
         */
        object OnClickSignOutButton : Event()

        /**
         * アカウント削除ボタン押下
         */
        object OnClickDeleteButton : Event()

        /**
         * ライフサイクル(onDestroyView)
         */
        object OnDestroyView : Event()
    }
}