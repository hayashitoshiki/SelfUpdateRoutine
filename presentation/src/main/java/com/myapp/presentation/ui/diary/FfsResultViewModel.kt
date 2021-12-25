package com.myapp.presentation.ui.diary

import androidx.lifecycle.viewModelScope
import com.myapp.presentation.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

/**
 * 振り返り_FFS振り返り確認画面 画面ロジック
 */
@HiltViewModel
class FfsResultViewModel @Inject constructor() :
    BaseViewModel<FfsResultContract.State, FfsResultContract.Effect, FfsResultContract.Event>() {

    override fun initState(): FfsResultContract.State {
        return FfsResultContract.State()
    }

    override fun handleEvents(event: FfsResultContract.Event) {
        when (event) {
            is FfsResultContract.Event.OnClickNextButton -> setEffect { FfsResultContract.Effect.NextNavigation }
        }
    }

    init {
        DiaryDispatcher.factState
            .onEach { setState { copy(fact = it) } }
            .launchIn(viewModelScope)
        DiaryDispatcher.findState
            .onEach { setState { copy(find = it) } }
            .launchIn(viewModelScope)
        DiaryDispatcher.learnState
            .onEach { setState { copy(learn = it) } }
            .launchIn(viewModelScope)
        DiaryDispatcher.statementState
            .onEach { setState { copy(statement = it) } }
            .launchIn(viewModelScope)
    }

}
