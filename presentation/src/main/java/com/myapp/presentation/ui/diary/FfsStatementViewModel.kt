package com.myapp.presentation.ui.diary

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * 振り返り_宣言画面 画面ロジック
 */
@HiltViewModel
class FfsStatementViewModel @Inject constructor() : DiaryBaseViewModel() {

    override suspend fun sendDispatcher(value: String) {
        DiaryDispatcher.setActions(DiaryDispatcherContract.Action.ChangeStatement(value))
    }
}
