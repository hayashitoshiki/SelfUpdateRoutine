package com.myapp.presentation.home.ui.slideshow

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

class DiaryAssessmentViewModel : DiaryItemViewModel() {

    @ExperimentalCoroutinesApi
    override fun changeText(input: String) {
        viewModelScope.launch {
            val inputNumber = input.toInt()
            Dispatcher.changeAssessment(inputNumber)
        }
    }

}