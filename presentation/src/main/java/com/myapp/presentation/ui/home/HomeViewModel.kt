package com.myapp.presentation.ui.home

import androidx.lifecycle.*
import com.myapp.domain.usecase.MissionStatementUseCase
import com.myapp.domain.usecase.ReportUseCase
import com.myapp.presentation.ui.MainDispatcher
import com.myapp.presentation.ui.MainDispatcherContract
import com.myapp.presentation.utils.base.BaseViewModel
import com.myapp.presentation.utils.expansion.img
import com.myapp.presentation.utils.expansion.isToday
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
) : BaseViewModel<HomeContract.State, HomeContract.Effect, HomeContract.Event>() {

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
        updateReportList()
        MainDispatcher.action.onEach {
            when(it) {
                is MainDispatcherContract.Action.AuthUpdate -> updateReportList()
            }
        }.launchIn(viewModelScope)
    }

    private fun updateReportList() = viewModelScope.launch {
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

    // Fab表示切り替え
    private fun changeFab() {
        setState { copy(isFabCheck = !state.value.isFabCheck) }
    }

    // 格言一覧画面へ遷移
    private fun navigateLearnList() {
        if (state.value.reportList.isEmpty()) {
            setEffect { HomeContract.Effect.ShowError(NullPointerException("レポートリストがありません")) }
        } else {
            setEffect { HomeContract.Effect.LearnListNavigation(state.value.reportList) }
        }
    }

    // 宣言詳細画面へ遷移
    private fun navigateStatementList() {
        if (state.value.reportList.isEmpty()) {
            setEffect { HomeContract.Effect.ShowError(NullPointerException("レポートリストがありません")) }
        } else {
            setEffect { HomeContract.Effect.StatementListNavigation(state.value.reportList) }
        }
    }

    // 画面破棄（初期化）処理
    private fun onDestroyView() {
        setState { copy(isFabCheck = false) }
        setEffect { HomeContract.Effect.OnDestroyView }
    }
}

