package com.myapp.presentation.ui.remember

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myapp.domain.model.entity.Report
import com.myapp.presentation.utils.img
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class RememberViewModel @AssistedInject constructor(@Assisted private val report: Report) : ViewModel() {

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

    // 画像
    private val _heartScoreImg = MutableLiveData<Int>()
    val heartScoreImg: LiveData<Int> = _heartScoreImg

    init {
        _factComment.value = report.ffsReport.factComment
        _findComment.value = report.ffsReport.findComment
        _learnComment.value = report.ffsReport.learnComment
        _statementComment.value = report.ffsReport.statementComment
        _heartScoreComment.value = when (report.weatherReport.heartScore.data) {
            in 0..20 -> "雨"
            in 21..40 -> "雨時々曇り"
            in 41..60 -> "曇り"
            in 61..80 -> "曇り時々晴れ"
            in 81..100 -> "晴れ"
            else -> "不正値"
        }
        _reasonComment.value = report.weatherReport.reasonComment
        _improveComment.value = report.weatherReport.improveComment
        _date.value = report.ffsReport.dataTime.toSectionDate()
        _heartScoreImg.value = report.weatherReport.heartScore.img
    }

    @AssistedFactory
    interface Factory {
        fun create(report: Report): RememberViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            factory: Factory,
            report: Report
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return factory.create(report) as T
            }
        }
    }
}
