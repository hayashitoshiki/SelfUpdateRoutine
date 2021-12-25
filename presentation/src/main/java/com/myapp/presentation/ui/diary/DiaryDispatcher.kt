package com.myapp.presentation.ui.diary

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * 振替入り画面用ディスパッチャー
 */
object DiaryDispatcher {

    private val _factState: MutableStateFlow<String> = MutableStateFlow("")
    val factState: StateFlow<String> = _factState
    private val _findState: MutableStateFlow<String> = MutableStateFlow("")
    val findState: StateFlow<String> = _findState
    private val _learnState: MutableStateFlow<String> = MutableStateFlow("")
    val learnState: StateFlow<String> = _learnState
    private val _statementState: MutableStateFlow<String> = MutableStateFlow("")
    val statementState: StateFlow<String> = _statementState
    private val _assessmentState: MutableStateFlow< Float> = MutableStateFlow(0f)
    val assessmentState: StateFlow<Float> = _assessmentState
    private val _reasonState: MutableStateFlow<String> = MutableStateFlow("")
    val reasonState: StateFlow<String> = _reasonState
    private val _improveState: MutableStateFlow<String> = MutableStateFlow("")
    val improveState: StateFlow<String> = _improveState

    /**
     * アクションとUIロジクの紐付け
     *
     * 他の画面の変更データをactionへ挿入してバインドしている各Viewへ流す
     *
     * @param action アクション
     */
    suspend fun setActions(action: DiaryDispatcherContract.Action) {
        when (action) {
            is DiaryDispatcherContract.Action.ChangeFact -> _factState.emit(action.value)
            is DiaryDispatcherContract.Action.ChangeFind -> _findState.emit(action.value)
            is DiaryDispatcherContract.Action.ChangeLearn -> _learnState.emit(action.value)
            is DiaryDispatcherContract.Action.ChangeStatement -> _statementState.emit(action.value)
            is DiaryDispatcherContract.Action.ChangeAssessment -> _assessmentState.emit(action.value)
            is DiaryDispatcherContract.Action.ChangeReason -> _reasonState.emit(action.value)
            is DiaryDispatcherContract.Action.ChangeImprove -> _improveState.emit(action.value)
        }
    }
}
