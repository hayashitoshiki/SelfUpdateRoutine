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


interface MissionStatementDispatcherContract {

    /**
     * 振り返り機能共有アクション
     *
     * 振り返り機能で画面間共有されるアクション定義
     */
    sealed class Action {
        data class ChangeFuneralText(val id: Long, val text: String) : Action()
        data class AddFuneral(val index: Int) : Action()
        data class DeleteFuneral(val index: Int) : Action()
        data class ChangeConstitutionText(val id: Long, val text: String) : Action()
        data class AddConstitution(val index: Int) : Action()
        data class DeleteConstitution(val index: Int) : Action()
        object Update : Action()
    }
}