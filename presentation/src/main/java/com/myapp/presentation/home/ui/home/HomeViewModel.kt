package com.myapp.presentation.home.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myapp.domain.model.entity.Report
import com.myapp.domain.usecase.ReportUseCase
import kotlinx.coroutines.launch

class HomeViewModel(private val reportUseCase: ReportUseCase) : ViewModel() {

    private val _report = MutableLiveData<List<Report>>()
    val report: LiveData<List<Report>> = _report

    init {
        viewModelScope.launch {
            _report.value = reportUseCase.getAllReport()
        }
    }

    fun getReport() {
    }
}