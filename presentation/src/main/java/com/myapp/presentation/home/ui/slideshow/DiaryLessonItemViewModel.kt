package com.myapp.presentation.home.ui.slideshow

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class DiaryLessonItemViewModel : DiaryItemViewModel() {

    @ExperimentalCoroutinesApi
    override fun changeText(input: String) {
        viewModelScope.launch {
            Dispatcher.changeLesson(input)
        }
    }

}