package com.myapp.presentation.ui.home

import androidx.lifecycle.*
import com.myapp.domain.model.entity.Report
import com.myapp.domain.usecase.MissionStatementUseCase
import com.myapp.domain.usecase.ReportUseCase
import com.myapp.presentation.utils.img
import com.myapp.presentation.utils.isToday
import kotlinx.coroutines.launch
import java.time.LocalDateTime

/**
 * ホーム画面　画面ロジック
 */
class HomeViewModel(
    private val reportUseCase: ReportUseCase,
    private val missionStatementUseCase: MissionStatementUseCase
) : ViewModel() {

    // 事実
    private val _factComment = MutableLiveData("")
    val factComment: LiveData<String> = _factComment

    // 発見
    private val _findComment = MutableLiveData("")
    val findComment: LiveData<String> = _findComment

    // 学び
    private val _learnComment = MutableLiveData("")
    val learnComment: LiveData<String> = _learnComment

    // 宣言
    private val _statementComment = MutableLiveData("")
    val statementComment: LiveData<String> = _statementComment

    // 評価
    private val _assessmentInputInt = MutableLiveData<Int>()
    val assessmentInputImg: LiveData<Int> = _assessmentInputInt

    // 理由
    private val _reasonComment = MutableLiveData("")
    val reasonComment: LiveData<String> = _reasonComment

    // 改善
    private val _improveComment = MutableLiveData("")
    val improveComment: LiveData<String> = _improveComment

    // 過去の振り返りレポートリスト
    private val _report = MutableLiveData<List<Report>>()
    val report: LiveData<List<Report>> = _report

    // メインコンテナの表示タイプ
    private val _mainContainerType = MutableLiveData<HomeFragmentMainContainerType<Report>>()
    val mainContainerType: LiveData<HomeFragmentMainContainerType<Report>> = _mainContainerType

    // 人生の目的
    private val _missionStatement = MutableLiveData("")
    val missionStatement: LiveData<String> = _missionStatement

    init {
        viewModelScope.launch {
            val reportList = reportUseCase.getAllReport()
            _report.value = reportList

            if (reportList.isEmpty()) {
                _mainContainerType.value = HomeFragmentMainContainerType.NotReport
                return@launch
            }
            val report = reportList.last()
            _factComment.value = report.ffsReport.factComment
            _findComment.value = report.ffsReport.findComment
            _learnComment.value = report.ffsReport.learnComment
            _statementComment.value = report.ffsReport.statementComment
            _assessmentInputInt.value = report.weatherReport.heartScore.img
            _reasonComment.value = report.weatherReport.reasonComment
            _improveComment.value = report.weatherReport.improveComment
            if (LocalDateTime.now().hour < 18) {
                _mainContainerType.value = HomeFragmentMainContainerType.Vision(report)
                missionStatementUseCase.getMissionStatement()?.purposeLife?.let {
                    _missionStatement.value = it
                }
            } else if (!report.ffsReport.dataTime.date.isToday()) {
                _mainContainerType.value = HomeFragmentMainContainerType.NotReport
                return@launch
            } else {
                _mainContainerType.value = HomeFragmentMainContainerType.Report(report)
            }

        }
    }
}
