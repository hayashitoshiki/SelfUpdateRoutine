package com.myapp.presentation.ui.diary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * 振り返り_FFS振り返り確認画面 画面ロジック
 */
@HiltViewModel
class FfsResultViewModel @Inject constructor() : ViewModel() {

    // 事実
    private val _factInputText = MutableLiveData("")
    val factInputText: LiveData<String> = _factInputText

    // 発見
    private val _findInputText = MutableLiveData("")
    val findInputText: LiveData<String> = _findInputText

    // 学び
    private val _learnInputText = MutableLiveData("")
    val learnInputText: LiveData<String> = _learnInputText

    // 宣言
    private val _statementInputText = MutableLiveData("")
    val statementInputText: LiveData<String> = _statementInputText

    init {
        DiaryDispatcher.factTextFlow.onEach { _factInputText.value = it }
            .launchIn(viewModelScope)
        DiaryDispatcher.findTextFlow.onEach { _findInputText.value = it }
            .launchIn(viewModelScope)
        DiaryDispatcher.learnTextFlow.onEach { _learnInputText.value = it }
            .launchIn(viewModelScope)
        DiaryDispatcher.statementTextFlow.onEach { _statementInputText.value = it }
            .launchIn(viewModelScope)
    }
}
