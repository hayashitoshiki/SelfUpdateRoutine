package com.myapp.presentation.ui.diary

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

/**
 * 振り返り_事実画面 画面ロジック
 */
class FfsFactViewModel : DiaryBaseViewModel() {

    init {
        DiaryDispatcher.factTextFlow.onEach { inputText.value = it }
            .take(1)
            .launchIn(viewModelScope)
        inputText.observeForever {
            viewModelScope.launch {
                DiaryDispatcher.changeFact(it)
            }
        }
    }

}