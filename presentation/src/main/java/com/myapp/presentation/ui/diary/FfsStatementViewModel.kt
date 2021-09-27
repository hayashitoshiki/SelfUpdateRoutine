package com.myapp.presentation.ui.diary

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

/**
 * 振り返り_宣言画面 画面ロジック
 */
@HiltViewModel
class FfsStatementViewModel @Inject constructor() : DiaryBaseViewModel() {

    init {
        DiaryDispatcher.statementTextFlow.onEach { inputText.value = it }
            .take(1)
            .launchIn(viewModelScope)
        inputText.observeForever {
            viewModelScope.launch {
                DiaryDispatcher.changeStatement(it)
            }
        }
    }
}
