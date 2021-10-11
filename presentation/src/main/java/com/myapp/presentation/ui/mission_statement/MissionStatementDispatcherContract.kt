package com.myapp.presentation.ui.mission_statement


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