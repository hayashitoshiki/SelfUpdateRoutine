package com.myapp.presentation.ui.mission_statement

import com.myapp.domain.model.entity.MissionStatement
import com.myapp.presentation.ui.home.HomeContract
import com.myapp.presentation.utils.base.BaseContract

interface MissionStatementListContract {

    /**
     * ミッションステートメント一覧画面 状態保持
     *
     * @property missionStatement ミッションステートメント
     * @property funeralList 理想の葬式リスト
     * @property isEnableFuneralList 理想の葬式カード表示制御
     * @property purposeLife 人生の目的
     * @property isEnablePurposeLife 人生の目的カード表示生業
     * @property constitutionList 憲法リスト
     * @property isEnableConstitutionList 憲法カード表示制御
     */
    data class State(
        var missionStatement: MissionStatement? = null,
        val funeralList: List<String> = listOf(),
        val isEnableFuneralList: Boolean = false,
        val purposeLife: String = "",
        val isEnablePurposeLife: Boolean = false,
        val constitutionList: List<String> = listOf(),
        val isEnableConstitutionList: Boolean = false
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

        /**
         * ライフサイクル(onDestroyView)
         */
        object OnDestroyView : Effect()
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

        /**
         * ライフサイクル(onDestroyView)
         */
        object OnDestroyView : Event()
    }
}
