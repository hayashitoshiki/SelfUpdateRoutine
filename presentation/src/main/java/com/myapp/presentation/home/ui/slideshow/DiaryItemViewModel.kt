package com.myapp.presentation.home.ui.slideshow

import androidx.lifecycle.ViewModel

abstract class DiaryItemViewModel : ViewModel() {
    abstract fun changeText(input: String)
}