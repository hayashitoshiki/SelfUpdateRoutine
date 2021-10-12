package com.myapp.presentation.ui.home

import androidx.lifecycle.*
import com.myapp.domain.usecase.MissionStatementUseCase
import com.myapp.domain.usecase.ReportUseCase
import com.myapp.presentation.utils.base.BaseAacViewModel
import com.myapp.presentation.utils.expansion.img
import com.myapp.presentation.utils.expansion.isToday
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

/**
 * ホーム画面　画面ロジック
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val reportUseCase: ReportUseCase,
    private val missionStatementUseCase: MissionStatementUseCase
) :
    BaseAacViewModel<HomeContract.State, HomeContract.Effect, HomeContract.Event>() {

    override fun initState(): HomeContract.State {
        return HomeContract.State()
    }

    override fun handleEvents(event: HomeContract.Event) = when(event) {
        is HomeContract.Event.OnClickFabButton -> changeFab()
        is HomeContract.Event.OnClickFabLearnButton ->  navigateLearnList()
        is HomeContract.Event.OnClickFabStatementButton -> navigateStatementList()
        is HomeContract.Event.OnClickReportButton -> setEffect { HomeContract.Effect.DiaryReportNavigation }
        is HomeContract.Event.OnClickReportCard -> setEffect { HomeContract.Effect.ReportDetailListNavigation(event.value) }
        is HomeContract.Event.OnDestroyView -> onDestroyView()
    }

    init {
        viewModelScope.launch {
            val reportList = reportUseCase.getAllReport()
            val isFabVisibility = reportList.isNotEmpty()
            val isReportListVisibility = reportList.isNotEmpty()
            val isNotReportListVisibility = reportList.isEmpty()

            if (reportList.isEmpty()) {
                val mainContainerType = HomeFragmentMainContainerType.NotReport
                setState {
                    copy(
                    reportList = reportList,
                    isFabVisibility = isFabVisibility,
                    isReportListVisibility = isReportListVisibility,
                    isNotReportListVisibility = isNotReportListVisibility,
                    mainContainerType = mainContainerType
                    )
                }
                return@launch
            }
            val report = reportList.last()
            val factComment = report.ffsReport.factComment
            val findComment = report.ffsReport.findComment
            val learnComment = report.ffsReport.learnComment
            val statementComment = report.ffsReport.statementComment
            val assessmentImg = report.weatherReport.heartScore.img
            val reasonComment = report.weatherReport.reasonComment
            val improveComment = report.weatherReport.improveComment
            val missionStatement = missionStatementUseCase.getMissionStatement()?.purposeLife ?: ""
            val mainContainerType =
                if (LocalDateTime.now().hour < 18) {
                    HomeFragmentMainContainerType.Vision
                } else if (!report.ffsReport.dataTime.date.isToday()) {
                    HomeFragmentMainContainerType.NotReport
                } else {
                    HomeFragmentMainContainerType.Report
                }
            setState {
                copy(
                    fact = factComment,
                    find = findComment,
                    learn = learnComment,
                    statement = statementComment,
                    assessmentImg = assessmentImg,
                    reason = reasonComment,
                    improve = improveComment,
                    missionStatement = missionStatement,
                    reportList = reportList,
                    isFabVisibility = isFabVisibility,
                    isReportListVisibility = isReportListVisibility,
                    isNotReportListVisibility = isNotReportListVisibility,
                    mainContainerType = mainContainerType
                )
            }
        }
    }

    // Fab表示切り替え
    private fun changeFab() {
        val enable = state.value?.isFabCheck ?: false
        setState { copy(isFabCheck = !enable) }
        setEffect { HomeContract.Effect.ChangeFabEnable(!enable) }
    }

    // 格言一覧画面へ遷移
    private fun navigateLearnList() {
        val reportList = state.value?.reportList ?:run {
            setEffect { HomeContract.Effect.ShowError(NullPointerException("レポートリストがありません")) }
            return
        }
        if (reportList.isEmpty()) {
            setEffect { HomeContract.Effect.ShowError(NullPointerException("レポートリストがありません")) }
            return
        }
        setEffect { HomeContract.Effect.LearnListNavigation(reportList) }
    }

    // 宣言詳細画面へ遷移
    private fun navigateStatementList() {
        val reportList = state.value?.reportList ?:run {
            setEffect { HomeContract.Effect.ShowError(NullPointerException("レポートリストがありません")) }
            return
        }
        if (reportList.isEmpty()) {
            setEffect { HomeContract.Effect.ShowError(NullPointerException("レポートリストがありません")) }
            return
        }
        setEffect { HomeContract.Effect.StatementListNavigation(reportList) }
    }

    // 画面破棄（初期化）処理
    private fun onDestroyView() {
        setState { copy(isFabCheck = false) }
        setEffect { HomeContract.Effect.OnDestroyView }
    }
}

