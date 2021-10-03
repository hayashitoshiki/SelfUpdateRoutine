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
        DiaryDispatcher.action.onEach {
            when (it) {
                is DiaryDispatcherContract.Action.ChangeFact -> setState { copy(fact = it.value) }
                is DiaryDispatcherContract.Action.ChangeFind -> setState { copy(find = it.value) }
                is DiaryDispatcherContract.Action.ChangeLesson -> setState { copy(learn = it.value) }
                is DiaryDispatcherContract.Action.ChangeStatement -> setState { copy(statement = it.value) }
            }
        }
            .launchIn(viewModelScope)
    }

}
