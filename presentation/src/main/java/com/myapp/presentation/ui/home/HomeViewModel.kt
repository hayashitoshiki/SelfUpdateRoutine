package com.myapp.presentation.ui.home

import androidx.lifecycle.*
import com.myapp.domain.model.entity.Report
import com.myapp.domain.usecase.ReportUseCase
import com.myapp.presentation.utill.Status
import com.myapp.presentation.utill.img
import com.myapp.presentation.utill.isToday
import kotlinx.coroutines.launch

/**
 * ホーム画面　画面ロジック
 */
class HomeViewModel(private val reportUseCase: ReportUseCase) : ViewModel() {

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

    // 今日の振り返りレポート
    private val _dalyReport = MutableLiveData<Status<Report>>(Status.Loading)
    val dalyReport: LiveData<Status<Report>> = _dalyReport

    init {
        viewModelScope.launch {
            val reportList = reportUseCase.getAllReport()
            _report.value = reportList

            if (reportList.isEmpty()) {
                _dalyReport.value = Status.Failure(IllegalAccessError("今日のデータが登録されていません。"))
                return@launch
            }
            val report = reportList.last()
            if (!report.ffsReport.dataTime.date.isToday()) {
                _dalyReport.value = Status.Failure(IllegalAccessError("今日のデータが登録されていません。"))
                return@launch
            }
            _factComment.value = report.ffsReport.factComment
            _findComment.value = report.ffsReport.findComment
            _learnComment.value = report.ffsReport.learnComment
            _statementComment.value = report.ffsReport.statementComment
            _assessmentInputInt.value = report.weatherReport.heartScore.img
            _reasonComment.value = report.weatherReport.reasonComment
            _improveComment.value = report.weatherReport.improveComment
            _dalyReport.value = Status.Success(report)
        }
    }
}