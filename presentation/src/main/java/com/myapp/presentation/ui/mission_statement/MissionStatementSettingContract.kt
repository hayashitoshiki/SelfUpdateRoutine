package com.myapp.presentation.ui.mission_statement

import com.myapp.presentation.R
import com.myapp.presentation.utils.base.BaseContract

interface MissionStatementSettingContract {

    /**
     * ミッションステートメント編集画面 状態保持
     *
     * @property isEnableConfirmButton 確定ボタンの活性・非活性制御
     * @property funeralListCount 理想の葬式リストのID連番
     * @property funeralList 理想の葬式リスト
     * @property funeralListDiffColor 理想の葬式変化文字列の文字色
     * @property purposeLife　人生の目的
     * @property purposeLifeDiffColor 人生の目的変化文字列の文字色
     * @property constitutionListCount 憲法リストのID連番
     * @property constitutionList 憲法リスト
     * @property constitutionListDiffColor 憲法変化文字列の文字色
     */
    data class State(
        val isEnableConfirmButton: Boolean = false,
        var funeralListCount: Long = 0,
        val funeralList: MutableList<Pair<Long, String>> = mutableListOf(),
        val funeralListDiffColor: Int = R.color.text_color_light_secondary,
        val purposeLife: String = "",
        val purposeLifeDiffColor: Int = R.color.text_color_light_secondary,
        var constitutionListCount: Long = 0,
        val constitutionList: MutableList<Pair<Long, String>> = mutableListOf(),
        val constitutionListDiffColor: Int = R.color.text_color_light_secondary,
    ) : BaseContract.State

    /**
     * UIイベント
     *
     */
    sealed class Effect : BaseContract.Effect {
        /**
         * 変更ボタンタップ後アクション
         */
        object NavigateMissionStatementSetting : Effect()

        /**
         * ライフサイクル(onDestroyView)
         */
        object OnDestroyView : Effect()

        /**
         * エラー表示
         */
        data class ShowError(val value: Throwable) : Effect()
    }

    /**
     * アクション
     *
     */
    sealed class Event : BaseContract.Event {
        /**
         * 人生の目的テキスト入力
         */
        data class OnChangePurposeText(val value: String) : Event()
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
