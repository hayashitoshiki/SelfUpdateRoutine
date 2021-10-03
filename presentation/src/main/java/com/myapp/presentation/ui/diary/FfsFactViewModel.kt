package com.myapp.presentation.ui.diary

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 振り返り_事実画面 画面ロジック
 */
@HiltViewModel
class FfsFactViewModel @Inject constructor() : DiaryBaseViewModel() {

    override suspend fun sendDispatcher(value: String) {
        DiaryDispatcher.handleActions(DiaryDispatcherContract.Action.ChangeFact(value))
    }

}
