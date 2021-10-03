package com.myapp.presentation.ui.diary

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 振り返り_改善案画面 画面ロジック
 */
@HiltViewModel
class WeatherImproveViewModel @Inject constructor() : DiaryBaseViewModel() {

    override suspend fun sendDispatcher(value: String) {
        DiaryDispatcher.handleActions(DiaryDispatcherContract.Action.ChangeImprove(value))
    }
}
