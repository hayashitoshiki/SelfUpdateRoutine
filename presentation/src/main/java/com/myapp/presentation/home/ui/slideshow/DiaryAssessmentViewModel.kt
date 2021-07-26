package com.myapp.presentation.home.ui.slideshow

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class DiaryAssessmentViewModel : DiaryItemViewModel() {

    @ExperimentalCoroutinesApi
    fun changeProgress(input: Int) {
        viewModelScope.launch {
            Dispatcher.changeAssessment(input)
        }
    }

}