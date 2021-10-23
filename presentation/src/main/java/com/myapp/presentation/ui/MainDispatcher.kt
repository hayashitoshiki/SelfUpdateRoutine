package com.myapp.presentation.ui

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

/**
 * アプリ全体共有Dispatcher
 */
object MainDispatcher {

    private val _action: MutableSharedFlow<MainDispatcherContract.Action> = MutableSharedFlow()
    val action: SharedFlow<MainDispatcherContract.Action> = _action

    /**
     * アクションとUIロジクの紐付け
     *
     * 他の画面の変更データをactionへ挿入してバインドしている各Viewへ流す
     *
     * @param action アクション
     */
    suspend fun setActions(action: MainDispatcherContract.Action) {
        _action.emit(action)
    }
}