package com.myapp.presentation.ui.diary

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

/**
 * 振り返り_改善案画面 画面ロジック
 */
class WeatherImproveViewModel : DiaryBaseViewModel() {

    init {
        DiaryDispatcher.improveTextFlow.onEach { inputText.value = it }
            .take(1)
            .launchIn(viewModelScope)
        inputText.observeForever {
            viewModelScope.launch {
                DiaryDispatcher.changeImprove(it)
            }
        }
    }

}