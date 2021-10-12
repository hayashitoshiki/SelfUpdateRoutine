package com.myapp.presentation.ui.mission_statement

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

/**
 * ミッションステートメント画面用ディスパッチャー
 */
object MissionStatementDispatcher {

    private val _action: MutableSharedFlow<MissionStatementDispatcherContract.Action> = MutableSharedFlow()
    val action: SharedFlow<MissionStatementDispatcherContract.Action> = _action

    /**
     * アクションとUIロジクの紐付け
     *
     * 他の画面の変更データをactionへ挿入してバインドしている各Viewへ流す
     *
     * @param action アクション
     */
    suspend fun setActions(action: MissionStatementDispatcherContract.Action) {
        _action.emit(action)
    }
}
