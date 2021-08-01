package com.myapp.presentation.ui.remember

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.myapp.domain.model.entity.Report

class RememberViewModel(private val report: Report) : ViewModel() {

    // 事実
    private val _factComment = MutableLiveData("")
    val factComment: LiveData<String> = _factComment

    // 発見
    private val _findComment = MutableLiveData("")
    val findComment: LiveData<String> = _findComment

    // 学び
    private val _learnComment = MutableLiveData("")
    val learnComment: LiveData<String> = _learnComment

    // 学び
    private val _statementComment = MutableLiveData("")
    val statementComment: LiveData<String> = _statementComment

    // 感情点数
    private val _heartScoreComment = MutableLiveData("")
    val heartScoreComment: LiveData<String> = _heartScoreComment

    // 理由
    private val _reasonComment = MutableLiveData("")
    val reasonComment: LiveData<String> = _reasonComment

    // 改善点
    private val _improveComment = MutableLiveData("")
    val improveComment: LiveData<String> = _improveComment

    // 日付
    private val _date = MutableLiveData("")
    val date: LiveData<String> = _date

    init {
        _factComment.value = report.ffsReport.factComment
        _findComment.value = report.ffsReport.findComment
        _learnComment.value = report.ffsReport.learnComment
        _statementComment.value = report.ffsReport.statementComment
        _heartScoreComment.value = report.weatherReport.heartScore.data.toString()
        _reasonComment.value = report.weatherReport.reasonComment
        _improveComment.value = report.weatherReport.improveComment
        _date.value = report.ffsReport.dataTime.toSectionDate()
    }

}