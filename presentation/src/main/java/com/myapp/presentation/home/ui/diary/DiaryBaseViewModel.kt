package com.myapp.presentation.home.ui.diary

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * 振り返り画面 BaseViewModel
 */
abstract class DiaryBaseViewModel : ViewModel() {

    val inputText = MutableLiveData("")

    val isButtonEnable = MediatorLiveData<Boolean>()

    init {
        isButtonEnable.addSource(inputText) { changeButtonEnable(it) }
    }

    private fun changeButtonEnable(text: String) {
        isButtonEnable.value = text.isNotEmpty()
    }

}