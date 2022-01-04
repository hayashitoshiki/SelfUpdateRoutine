package com.myapp.presentation.ui.mission_statement

import com.myapp.domain.model.entity.MissionStatement
import com.myapp.presentation.utils.base.BaseContract

interface MissionStatementListContract {

    /**
     * ミッションステートメント一覧画面 状態保持
     *
     * @property missionStatement ミッションステートメント
     * @property funeralList 理想の葬式リスト
     * @property purposeLife 人生の目的
     * @property constitutionList 憲法リスト
     */
    data class State(
        var missionStatement: MissionStatement? = null,
        val funeralList: List<String> = listOf(),
        val purposeLife: String = "",
        val constitutionList: List<String> = listOf(),
    ) : BaseContract.State

    /**
     * UIイベント
     *
     */
    sealed class Effect : BaseContract.Effect {
        /**
         * 変更ボタンタップ後アクション
         *
         * @property value ミッションステートメント
         */
        data class NavigateMissionStatementSetting(val value: MissionStatement?) : Effect()
    }

    /**
     * アクション
     *
     */
    sealed class Event : BaseContract.Event {
        /**
         * 変更ボタン押下
         */
        object OnClickChangeButton : Event()
    }
}
