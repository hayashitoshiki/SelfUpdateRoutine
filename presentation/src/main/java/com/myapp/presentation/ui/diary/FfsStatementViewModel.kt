package com.myapp.presentation.ui.diary

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 振り返り_宣言画面 画面ロジック
 */
@HiltViewModel
class FfsStatementViewModel @Inject constructor() : DiaryBaseViewModel() {

    override fun handleEvents(event: DiaryBaseContract.Event) {
        when (event) {
            is DiaryBaseContract.Event.OnChangeText -> changeText(event.value)
            is DiaryBaseContract.Event.OnClickNextButton -> setEffect { DiaryBaseContract.Effect.NextNavigation }
        }
    }

    private fun changeText(value: String) {
        val first = Regex("^(私は|わたしは)")
        val middle = Regex("(でいる|ている)")
        val firstHalf = Regex("^(私は|わたしは)(.){2,}(でいる|ている)")
        val last = Regex("((です)+[!！.。★☆]*)$")
        val latterHalf = Regex("((でいる|ている)(男|女|人|(.){2,})(です)+[!！.。★☆]*)$")
        val (isButtonEnable,hintText, hintVisibility) =
        if (value.length < 2) {
            Triple(false,"",false)
        } else if (!first.containsMatchIn(value)) {
            Triple(false,"文章の先頭は「わたしは」ではじめてください",true)
        } else if (!middle.containsMatchIn(value)) {
            Triple(false,"文章は現在進行形にしてください",true)
        } else if (!firstHalf.containsMatchIn(value)) {
            Triple(false,"動詞を入れてください",true)
        } else if(!last.containsMatchIn(value)) {
            Triple(false,"最後は「です」と宣言してください",true)
        } else if(!latterHalf.containsMatchIn(value)) {
            Triple(false,"あなたは何者ですか？",true)
        } else {
            Triple(true,"",false)
        }
        setState { copy(inputText = value, hintText = hintText, hintVisibility = hintVisibility, isButtonEnable = isButtonEnable) }
        viewModelScope.launch {
            sendDispatcher(value)
        }
    }

    override suspend fun sendDispatcher(value: String) {
        DiaryDispatcher.setActions(DiaryDispatcherContract.Action.ChangeStatement(value))
    }

}
