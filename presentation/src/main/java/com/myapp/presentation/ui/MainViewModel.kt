package com.myapp.presentation.ui

import androidx.lifecycle.viewModelScope
import com.myapp.domain.usecase.AuthUseCase
import com.myapp.presentation.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val authUseCase: AuthUseCase) :
    BaseViewModel<MainContract.State, MainContract.Effect, MainContract.Event>() {

    override fun initState(): MainContract.State {
        return MainContract.State()
    }

    override fun handleEvents(event: MainContract.Event) = when(event) {
        is MainContract.Event.OnMoveScreen -> { setState { copy(currentScreen = event.screen) } }
    }

    init {
        updateAccountDetail()
        MainDispatcher.action.onEach {
            when(it) {
                is MainDispatcherContract.Action.AuthUpdate -> updateAccountDetail()
            }
        }.launchIn(viewModelScope)
    }

    /**
     * アカウント表示情報更新
     *
     */
    private fun updateAccountDetail() {
       authUseCase.getAccountDetail()?.let {
           setState { copy(accountName = it.name, accountEmail = it.email) }
        } ?: run {
           setState { copy(accountName = "未ログイン", accountEmail = "") }
        }
    }

}