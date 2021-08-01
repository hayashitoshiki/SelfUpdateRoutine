package com.myapp.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myapp.domain.model.entity.Report
import com.myapp.presentation.utill.img

/**
 * 簡易振り返りカード 画面ロジック
 */
class DiaryCardViewModel(rep: Report) : ViewModel() {

    private val _report = MutableLiveData<Report>()
    val report: LiveData<Report> = _report

    private val _heartScoreImg = MutableLiveData<Int>()
    val heartScoreImg: LiveData<Int> = _heartScoreImg

    init {
        _report.observeForever {
            _heartScoreImg.value = it.weatherReport.heartScore.img
        }
        _report.value = rep
    }
}