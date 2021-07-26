package com.myapp.presentation.home.ui.slideshow

import androidx.lifecycle.*
import com.myapp.domain.dto.AllReportInputDto
import com.myapp.domain.usecase.ReportUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class SlideshowViewModel(private val reportUseCase: ReportUseCase) : ViewModel() {

    // 事実
    @ExperimentalCoroutinesApi
    val factInputText: LiveData<String> = Dispatcher.factTextFlow.asLiveData()

    // 発見
    @ExperimentalCoroutinesApi
    val noticeInputText: LiveData<String> = Dispatcher.noticeTextFlow.asLiveData()

    // 学び
    @ExperimentalCoroutinesApi
    val learnInputText: LiveData<String> = Dispatcher.learnTextFlow.asLiveData()

    // 宣言
    @ExperimentalCoroutinesApi
    val statementInputText: LiveData<String> = Dispatcher.statementTextFlow.asLiveData()

    // 評価
    @ExperimentalCoroutinesApi
    val assessmentInputInt: LiveData<Int> = Dispatcher.assessmentTextFlow.asLiveData()

    @ExperimentalCoroutinesApi
    val assessmentInputText: LiveData<String> = assessmentInputInt.map {
        when (it) {
            in 0..20 -> "雨"
            in 21..40 -> "雨時々曇り"
            in 41..60 -> "曇り"
            in 61..80 -> "曇り時々晴れ"
            in 81..100 -> "晴れ"
            else -> "不正値"
        }
    }

    // 理由
    @ExperimentalCoroutinesApi
    val reasonInputText: LiveData<String> = Dispatcher.reasonTextFlow.asLiveData()

    // 改善
    @ExperimentalCoroutinesApi
    val planInputText: LiveData<String> = Dispatcher.actionTextFlow.asLiveData()

    // 振り返り日記登録
    @ExperimentalCoroutinesApi
    fun saveReport() {
        val section11 = factInputText.value ?: return
        val section12 = noticeInputText.value ?: return
        val section13 = learnInputText.value ?: return
        val section14 = statementInputText.value ?: return
        val section21 = assessmentInputInt.value ?: return
        val section22 = reasonInputText.value ?: return
        val section23 = planInputText.value ?: return
        val allReportInputDto = AllReportInputDto(
            section11, section12, section13, section14, section21, section22, section23
        )
        viewModelScope.launch {
            reportUseCase.saveReport(allReportInputDto)
        }
    }

}