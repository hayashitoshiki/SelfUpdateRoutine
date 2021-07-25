package com.myapp.presentation.home.ui.slideshow

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow

/**
 * 実績入力用ディスパッチャー
 */
object Dispatcher {

    // 事実
    @ExperimentalCoroutinesApi
    private val factTextChannel = MutableStateFlow("")

    @ExperimentalCoroutinesApi
    val factTextFlow: SharedFlow<String> = factTextChannel

    // 発見
    @ExperimentalCoroutinesApi
    private val noticeTextChannel = MutableStateFlow("")

    @ExperimentalCoroutinesApi
    val noticeTextFlow: SharedFlow<String> = noticeTextChannel

    // 教訓
    @ExperimentalCoroutinesApi
    private val learnTextChannel = MutableStateFlow("")

    @ExperimentalCoroutinesApi
    val learnTextFlow: SharedFlow<String> = learnTextChannel

    // 宣言
    @ExperimentalCoroutinesApi
    private val statementTextChannel = MutableStateFlow("")

    @ExperimentalCoroutinesApi
    val statementTextFlow: SharedFlow<String> = statementTextChannel

    // 評判
    @ExperimentalCoroutinesApi
    private val assessmentTextChannel = MutableStateFlow(50)

    @ExperimentalCoroutinesApi
    val assessmentTextFlow: SharedFlow<Int> = assessmentTextChannel

    // 改善案
    @ExperimentalCoroutinesApi
    private val actionTextChannel = MutableStateFlow("")

    @ExperimentalCoroutinesApi
    val actionTextFlow: SharedFlow<String> = actionTextChannel

    // 理由
    @ExperimentalCoroutinesApi
    private val reasonTextChannel = MutableStateFlow("")

    @ExperimentalCoroutinesApi
    val reasonTextFlow: SharedFlow<String> = reasonTextChannel


    // 事実内容変更
    @ExperimentalCoroutinesApi
    suspend fun changeFact(inputText: String) {

        factTextChannel.emit(inputText)
    }

    // 発見内容変更
    @ExperimentalCoroutinesApi
    suspend fun changeDiscover(inputText: String) {

        noticeTextChannel.emit(inputText)
    }

    // 教訓内容変更
    @ExperimentalCoroutinesApi
    suspend fun changeLesson(inputText: String) {

        learnTextChannel.emit(inputText)
    }

    // 宣言内容変更
    @ExperimentalCoroutinesApi
    suspend fun changeStatement(inputText: String) {

        statementTextChannel.emit(inputText)
    }

    // 評価内容変更
    @ExperimentalCoroutinesApi
    suspend fun changeAssessment(inputNumber: Int) {

        assessmentTextChannel.emit(inputNumber)
    }

    // 改善案内容変更
    @ExperimentalCoroutinesApi
    suspend fun changeAction(inputText: String) {

        actionTextChannel.emit(inputText)
    }

    // 理由内容変更
    @ExperimentalCoroutinesApi
    suspend fun changeReason(inputText: String) {

        reasonTextChannel.emit(inputText)
    }
}