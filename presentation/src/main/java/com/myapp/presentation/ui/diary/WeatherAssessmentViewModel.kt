package com.myapp.presentation.ui.diary

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 振り返り_評価画面 画面ロジック
 */
@HiltViewModel
class WeatherAssessmentViewModel @Inject constructor() : DiaryBaseViewModel() {

    override fun initState(): DiaryBaseContract.State {
        return DiaryBaseContract.State(inputText = "0.5")
    }

    override suspend fun sendDispatcher(value: String) {
        DiaryDispatcher.setActions(DiaryDispatcherContract.Action.ChangeAssessment(value.toFloat()))
    }
}
