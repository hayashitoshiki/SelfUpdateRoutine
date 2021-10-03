package com.myapp.presentation.ui.home

import androidx.lifecycle.*
import com.myapp.domain.model.entity.Report
import com.myapp.domain.usecase.MissionStatementUseCase
import com.myapp.domain.usecase.ReportUseCase
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

    // Fabボタン表示非表示制御
    private val _isFabVisibility = MediatorLiveData<Boolean>()
    val isFabVisibility: LiveData<Boolean> = _isFabVisibility

    // レポートリスト表示非表示制御
    private val _isReportListVisibility = MediatorLiveData<Boolean>()
    val isReportListVisibility: LiveData<Boolean> = _isReportListVisibility

    // レポート未登録メッセージ表示非表示制御
    private val _isNotReportListVisibility = MediatorLiveData<Boolean>()
    val isNotReportListVisibility: LiveData<Boolean> = _isNotReportListVisibility

    init {
        _isFabVisibility.addSource(_report) { _isFabVisibility.value = _report.value?.isNotEmpty() }
        _isReportListVisibility.addSource(_report) { _isReportListVisibility.value = _report.value?.isNotEmpty() }
        _isNotReportListVisibility.addSource(_report) { _isNotReportListVisibility.value = _report.value?.isEmpty() }

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
