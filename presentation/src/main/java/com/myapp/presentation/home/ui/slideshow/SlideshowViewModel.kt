package com.myapp.presentation.home.ui.slideshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.map
import kotlinx.coroutines.ExperimentalCoroutinesApi

class SlideshowViewModel : ViewModel() {

    // 事実
    @ExperimentalCoroutinesApi
    val factInputText: LiveData<String> = Dispatcher.factTextFlow.asLiveData()

    // 発見
    @ExperimentalCoroutinesApi
    val noticeInputText: LiveData<String> = Dispatcher.noticeTextFlow.asLiveData()

    // 学び
    @ExperimentalCoroutinesApi
    val learnInputText: LiveData<String> = Dispatcher.learnTextFlow.asLiveData()

    // 宣言
    @ExperimentalCoroutinesApi
    val statementInputText: LiveData<String> = Dispatcher.statementTextFlow.asLiveData()

    // 評価
    @ExperimentalCoroutinesApi
    val assessmentInputInt: LiveData<Int> = Dispatcher.assessmentTextFlow.asLiveData()

    @ExperimentalCoroutinesApi
    val assessmentInputText: LiveData<String> = assessmentInputInt.map {
        when (it) {
            in 0..20 -> "雨"
            in 21..40 -> "雨時々曇り"
            in 41..60 -> "曇り"
            in 61..80 -> "曇り時々晴れ"
            in 81..100 -> "晴れ"
            else -> "不正値"
        }
    }

    // 理由
    @ExperimentalCoroutinesApi
    val reasonInputText: LiveData<String> = Dispatcher.reasonTextFlow.asLiveData()

    // 改善
    @ExperimentalCoroutinesApi
    val planInputText: LiveData<String> = Dispatcher.actionTextFlow.asLiveData()

}