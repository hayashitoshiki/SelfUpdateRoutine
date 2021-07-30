package com.myapp.presentation.home.ui.diary

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

/**
 * 振り返り_理由画面 画面ロジック
 */
class WeatherReasonViewModel : DiaryBaseViewModel() {

    init {
        DiaryDispatcher.reasonTextFlow.onEach { inputText.value = it }
            .take(1)
            .launchIn(viewModelScope)
        inputText.observeForever {
            viewModelScope.launch {
                DiaryDispatcher.changeReason(it)
            }
        }
    }

}