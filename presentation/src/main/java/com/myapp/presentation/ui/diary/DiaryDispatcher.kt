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
    suspend fun handleActions(action: DiaryDispatcherContract.Action) {
        _action.emit(action)
    }
}

interface DiaryDispatcherContract {
    sealed class Action {
        data class ChangeFact(val value: String) : Action()
        data class ChangeFind(val value: String) : Action()
        data class ChangeLesson(val value: String) : Action()
        data class ChangeStatement(val value: String) : Action()
        data class ChangeAssessment(val value: Float) : Action()
        data class ChangeReason(val value: String) : Action()
        data class ChangeImprove(val value: String) : Action()
    }
}