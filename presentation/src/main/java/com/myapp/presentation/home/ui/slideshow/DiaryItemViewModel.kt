package com.myapp.presentation.home.ui.slideshow

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class DiaryItemViewModel : ViewModel() {
    val inputText = MutableLiveData("")
}