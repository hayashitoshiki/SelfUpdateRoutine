package com.myapp.presentation.ui.mission_statement

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

/**
 * ミッションステートメント画面用ディスパッチャー
 */
object MissionStatementDispatcher {

    // 憲法_リスト修正
    private val _constitutionText = MutableSharedFlow<Pair<Int, String>>()
    val constitutionText: SharedFlow<Pair<Int, String>> = _constitutionText

    // 憲法_追加ボタン
    private val _constitutionPlusButton = MutableSharedFlow<Int>()
    val constitutionPlusButton: SharedFlow<Int> = _constitutionPlusButton

    // 憲法_削除ボタン
    private val _constitutionMinusButton = MutableSharedFlow<Int>()
    val constitutionMinusButton: SharedFlow<Int> = _constitutionMinusButton

    // 理想の葬式リスト修正
    private val _funeralText = MutableSharedFlow<Pair<Int, String>>()
    val funeralText: SharedFlow<Pair<Int, String>> = _funeralText

    // 理想の葬式_追加ボタン
    private val _funeralPlusButton = MutableSharedFlow<Int>()
    val funeralPlusButton: SharedFlow<Int> = _funeralPlusButton

    // 理想の葬式_削除ボタン
    private val _funeralMinusButton = MutableSharedFlow<Int>()
    val funeralMinusButton: SharedFlow<Int> = _funeralMinusButton

    private val _updateMessage = MutableSharedFlow<String>()
    val updateMessage: SharedFlow<String> = _updateMessage

    // 理想の葬式_リスト修正
    suspend fun changeFuneralText(
        index: Int,
        text: String
    ) {
        _funeralText.emit(Pair(index, text))
    }

    // 理想の葬式_追加ボタン
    suspend fun addFuneral(index: Int) {
        _funeralPlusButton.emit(index)
    }

    // 理想の葬式_削除ボタン
    suspend fun deleteFuneral(index: Int) {
        _funeralMinusButton.emit(index)
    }

    // 憲法_リスト修正
    suspend fun changeConstitutionText(
        index: Int,
        text: String
    ) {
        _constitutionText.emit(Pair(index, text))
    }

    // 憲法_追加ボタン
    suspend fun addConstitution(index: Int) {
        _constitutionPlusButton.emit(index)
    }

    // 憲法_削除ボタン
    suspend fun deleteConstitution(index: Int) {
        _constitutionMinusButton.emit(index)
    }

    // ミッションステートメント更新通知
    suspend fun updateMissionStatement() {
        _updateMessage.emit("update")
    }

}