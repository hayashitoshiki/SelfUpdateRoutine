package com.myapp.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.myapp.domain.model.entity.Report
import com.myapp.presentation.utils.expansion.img
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

/**
 * 簡易振り返りカード 画面ロジック
 */
class DiaryCardViewModel @AssistedInject constructor(@Assisted rep: Report) : ViewModel() {

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

    @AssistedFactory
    interface Factory {
        fun create(report: Report): DiaryCardViewModel
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
