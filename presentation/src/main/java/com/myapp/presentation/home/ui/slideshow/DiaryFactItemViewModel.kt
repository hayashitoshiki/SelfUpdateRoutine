package com.myapp.presentation.home.ui.slideshow

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class DiaryFactItemViewModel : DiaryItemViewModel() {

    init {
        inputText.observeForever {
            viewModelScope.launch {
                Dispatcher.changeFact(it)
            }
        }
    }

}