package com.myapp.presentation.ui

interface MainDispatcherContract {

    /**
     * アプリ全体共有アクション
     *
     * アプリ全体で共有されるアクション定義
     */
    sealed class Action {

        /**
         * 認証情報更新
         */
        object AuthUpdate : Action()
    }
}