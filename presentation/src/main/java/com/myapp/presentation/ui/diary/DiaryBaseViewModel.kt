package com.myapp.presentation.ui.diary

import androidx.lifecycle.viewModelScope
import com.myapp.presentation.utils.base.BaseViewModel
import kotlinx.coroutines.launch

/**
 * 振り返り画面 BaseViewModel
 */
abstract class DiaryBaseViewModel :
    BaseViewModel<DiaryBaseContract.State, DiaryBaseContract.Effect, DiaryBaseContract.Event>() {

    override fun initState(): DiaryBaseContract.State {
        return DiaryBaseContract.State()
    }

    override fun handleEvents(event: DiaryBaseContract.Event) {
        when (event) {
            is DiaryBaseContract.Event.OnChangeText -> changeText(event.value)
            is DiaryBaseContract.Event.OnClickNextButton -> setEffect { DiaryBaseContract.Effect.NextNavigation }
        }
    }

    private fun changeText(value: String) {
        setState { copy(inputText = value, isButtonEnable = value.isNotBlank()) }
        viewModelScope.launch {
            sendDispatcher(value)
        }
    }

    /**
     * 値変更共有定義
     *
     * @param value 変更した値
     */
    abstract suspend fun sendDispatcher(value: String)
}
