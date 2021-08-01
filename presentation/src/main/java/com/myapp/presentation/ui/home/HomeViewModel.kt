package com.myapp.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.domain.model.entity.Report
import com.myapp.domain.usecase.ReportUseCase
import com.myapp.presentation.utill.Status
import com.myapp.presentation.utill.isToday
import kotlinx.coroutines.launch

/**
 * ホーム画面　画面ロジック
 */
class HomeViewModel(private val reportUseCase: ReportUseCase) : ViewModel() {

    private val _report = MutableLiveData<List<Report>>()
    val report: LiveData<List<Report>> = _report

    private val _dalyReport = MutableLiveData<Status<Report>>(Status.Loading)
    val dalyReport: LiveData<Status<Report>> = _dalyReport

    init {
        viewModelScope.launch {
            _report.value = reportUseCase.getAllReport()
            val report = _report.value?.last()
            if (report != null && report.ffsReport.dataTime.date.isToday()) {
                _dalyReport.value = Status.Success(report)
            } else {
                _dalyReport.value = Status.Failure(IllegalAccessError("今日のデータが登録されていません。"))
            }
        }
    }
}