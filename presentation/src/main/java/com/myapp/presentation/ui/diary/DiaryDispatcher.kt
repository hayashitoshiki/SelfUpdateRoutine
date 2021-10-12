package com.myapp.presentation.ui.diary

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

/**
 * 振替入り画面用ディスパッチャー
 */
object DiaryDispatcher {

    private val _action: MutableSharedFlow<DiaryDispatcherContract.Action> = MutableSharedFlow()
    val action: SharedFlow<DiaryDispatcherContract.Action> = _action

    /**
     * アクションとUIロジクの紐付け
     *
     * 他の画面の変更データをactionへ挿入してバインドしている各Viewへ流す
     *
     * @param action アクション
     */
    suspend fun setActions(action: DiaryDispatcherContract.Action) {
        _action.emit(action)
    }
}
