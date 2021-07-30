package com.myapp.presentation.ui.diary

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.myapp.presentation.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

/**
 * 振り返り_評価画面 画面ロジック
 */
class WeatherAssessmentViewModel : DiaryBaseViewModel() {

    val assessmentValue = MutableLiveData(50)
    val assessmentImg = MediatorLiveData<Int>()

    init {
        DiaryDispatcher.assessmentTextFlow.onEach { assessmentValue.value = it }
            .take(1)
            .launchIn(viewModelScope)
        assessmentValue.observeForever {
            GlobalScope.launch {
                DiaryDispatcher.changeAssessment(it)
            }

            assessmentImg.value = when (it) {
                in 0..20 -> R.drawable.ic_rain_96dp
                in 21..40 -> R.drawable.ic_rain_and_cloudy_96dp
                in 41..60 -> R.drawable.ic_cloudy_96dp
                in 61..80 -> R.drawable.ic_cloudy_and_sunny_96dp
                in 81..100 -> R.drawable.ic_sunny_96dp
                else -> R.drawable.ic_cloudy_96dp
            }
        }
    }

    fun changeProgress(input: Int) {
        viewModelScope.launch {
            DiaryDispatcher.changeAssessment(input)
        }
    }

}