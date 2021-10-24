package com.myapp.presentation.ui

import com.myapp.presentation.utils.base.BaseContract

interface MainContract {

    /**
     * メイン画面 状態保持
     *
     * @property accountName アカウント名
     * @property accountEmail アカウントのメールアドレス
     */
    data class State(
        var accountName: String = "",
        val accountEmail: String = ""
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
    sealed class Event : BaseContract.Event
}