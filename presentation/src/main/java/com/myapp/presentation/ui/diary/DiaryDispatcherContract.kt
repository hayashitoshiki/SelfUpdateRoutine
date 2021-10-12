package com.myapp.presentation.ui.diary

interface DiaryDispatcherContract {

    /**
     * 振り返り機能共有アクション
     *
     * 振り返り機能で画面間共有されるアクション定義
     */
    sealed class Action {
        data class ChangeFact(val value: String) : Action()
        data class ChangeFind(val value: String) : Action()
        data class ChangeLearn(val value: String) : Action()
        data class ChangeStatement(val value: String) : Action()
        data class ChangeAssessment(val value: Float) : Action()
        data class ChangeReason(val value: String) : Action()
        data class ChangeImprove(val value: String) : Action()
    }
}