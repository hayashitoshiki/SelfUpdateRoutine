package com.myapp.presentation.ui.diary

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import javax.inject.Inject

/**
 * 振り返り_理由画面 画面ロジック
 */
@HiltViewModel
class WeatherReasonViewModel @Inject constructor() : DiaryBaseViewModel() {

    init {
        DiaryDispatcher.action.filter { it is DiaryDispatcherContract.Action.ChangeReason }
            .take(1)
            .onEach { setState { copy(inputText = (it as DiaryDispatcherContract.Action.ChangeReason).value) } }
            .launchIn(viewModelScope)
    }

    override suspend fun sendDispatcher(value: String) {
        DiaryDispatcher.handleActions(DiaryDispatcherContract.Action.ChangeReason(value))
    }
}
