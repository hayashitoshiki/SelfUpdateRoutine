package com.myapp.presentation.ui.home

import com.myapp.domain.model.entity.Report
import com.myapp.domain.model.value.HeartScore
import com.myapp.presentation.utils.base.BaseContract
import com.myapp.presentation.utils.expansion.img

interface HomeContract {
    /**
     * ホーム画面　状態保持
     *
     * @property fact 事象
     * @property find 発見
     * @property learn 教訓
     * @property statement 宣言
     * @property assessmentImg 点数画像
     * @property assessmentInputText 点数テキスト
     * @property reason 理由
     * @property improve 改善案
     * @property reportList レポートリスト
     * @property mainContainerType メインコンテンツ表示タイプ
     * @property missionStatement ミッションステートメント
     * @property isFabCheck FabボタンON/OFF制御
     * @property isFabVisibility Fabボタン表示/非表示制御
     * @property isReportListVisibility レポートリスト表示制御
     * @property isNotReportListVisibility レポート未登録文言表示制御
     */
    data class State(
        val fact: String = "",
        val find: String = "",
        val learn: String = "",
        val statement: String = "",
        val assessmentImg: Int = HeartScore(50).img,
        val assessmentInputText: String = "",
        val reason: String = "",
        val improve: String = "",
        val reportList: List<Report> = listOf(),
        val mainContainerType: HomeFragmentMainContainerType = HomeFragmentMainContainerType.NotReport,
        val missionStatement: String = "",
        val isFabCheck: Boolean = false,
        val isFabVisibility: Boolean = false,
        val isReportListVisibility: Boolean = false,
        val isNotReportListVisibility: Boolean = false
    ) : BaseContract.State

    /**
     * UIイベント
     *
     */
    sealed class Effect : BaseContract.Effect {
        /**
         * Fab表示切り替え
         */
        data class ChangeFabEnable(val value: Boolean) : Effect()

        /**
         * 宣言一覧画面へ遷移
         */
        data class StatementListNavigation(val value: List<Report>) : Effect()

        /**
         * 教訓一覧画面へ遷移
         */
        data class LearnListNavigation(val value: List<Report>) : Effect()

        /**
         *　振り返り詳細画面へ遷移
         */
        data class ReportDetailListNavigation(val value: Report) : Effect()

        /**
         * 振り返り記入画面へ遷移
         */
        object DiaryReportNavigation : Effect()

        /**
         * ライフサイクル(onDestroyView)
         */
        object OnDestroyView : Effect()

        /**
         * エラー表示
         */
        data class ShowError(val throwable: Throwable) : Effect()
    }

    /**
     * アクション
     *
     */
    sealed class Event : BaseContract.Event {

        /**
         * Fabボタン押下
         */
        object OnClickFabButton : Event()

        /**
         * Fab_格言ボタン押下
         */
        object OnClickFabLearnButton : Event()

        /**
         * Fab_宣言ボタン押下
         */
        object OnClickFabStatementButton : Event()

        /**
         * 振り返りカード押下
         */
        data class OnClickReportCard(val value: Report) : Event()

        /**
         * 振り返り入力ボタン押下
         */
        object OnClickReportButton : Event()

        /**
         * ライフサイクル(onDestroyView)
         */
        object OnDestroyView : Event()
    }
}
