package com.myapp.presentation.ui

import com.myapp.presentation.utils.base.BaseContract

interface MainContract {

    /**
     * メイン画面 状態保持
     *
     * @property accountName アカウント名
     * @property accountEmail アカウントのメールアドレス
     * @property currentScreen 遷移した画面
     */
    data class State(
        var accountName: String = "",
        val accountEmail: String = "",
        val currentScreen: Screens = Screens.DrawerScreens.HomeScreen
    ) : BaseContract.State

    /**
     * UIイベント
     *
     */
    sealed class Effect : BaseContract.Effect

    /**
     * アクション
     *
     */
    sealed class Event : BaseContract.Event {
        /**
         * 画面遷移
         *
         * @property screen 遷移先画面
         */
        data class OnMoveScreen(val screen: Screens): Event()
    }
}