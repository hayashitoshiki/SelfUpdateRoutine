package com.myapp.presentation.home.ui.slideshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
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
    val assessmentInputText: LiveData<Int> = Dispatcher.assessmentTextFlow.asLiveData()

    // 理由
    @ExperimentalCoroutinesApi
    val reasonInputText: LiveData<String> = Dispatcher.reasonTextFlow.asLiveData()

    // 改善
    @ExperimentalCoroutinesApi
    val planInputText: LiveData<String> = Dispatcher.actionTextFlow.asLiveData()
}