package com.myapp.presentation.ui.diary

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * 振替入り画面用ディスパッチャー
 */
object DiaryDispatcher {

    // 事実
    private val factTextMutableFlow = MutableStateFlow("")
    val factTextFlow: StateFlow<String> = factTextMutableFlow

    // 発見
    private val findTextMutableFlow = MutableStateFlow("")
    val findTextFlow: StateFlow<String> = findTextMutableFlow

    // 教訓
    private val learnTextMutableFlow = MutableStateFlow("")
    val learnTextFlow: StateFlow<String> = learnTextMutableFlow

    // 宣言
    private val statementTextMutableFlow = MutableStateFlow("")
    val statementTextFlow: StateFlow<String> = statementTextMutableFlow

    // 評判
    private val assessmentTextMutableFlow = MutableStateFlow(50)
    val assessmentTextFlow: StateFlow<Int> = assessmentTextMutableFlow

    // 改善案
    private val improveTextMutableFlow = MutableStateFlow("")
    val improveTextFlow: StateFlow<String> = improveTextMutableFlow

    // 理由
    private val reasonTextMutableFlow = MutableStateFlow("")
    val reasonTextFlow: StateFlow<String> = reasonTextMutableFlow

    // 事実内容変更
    suspend fun changeFact(inputText: String) {
        factTextMutableFlow.emit(inputText)
    }

    // 発見内容変更
    suspend fun changeFind(inputText: String) {
        findTextMutableFlow.emit(inputText)
    }

    // 教訓内容変更
    suspend fun changeLesson(inputText: String) {
        learnTextMutableFlow.emit(inputText)
    }

    // 宣言内容変更
    suspend fun changeStatement(inputText: String) {
        statementTextMutableFlow.emit(inputText)
    }

    // 評価内容変更
    suspend fun changeAssessment(inputNumber: Int) {
        assessmentTextMutableFlow.emit(inputNumber)
    }

    // 理由内容変更
    suspend fun changeReason(inputText: String) {
        reasonTextMutableFlow.emit(inputText)
    }

    // 改善案内容変更
    suspend fun changeImprove(inputText: String) {
        improveTextMutableFlow.emit(inputText)
    }

}