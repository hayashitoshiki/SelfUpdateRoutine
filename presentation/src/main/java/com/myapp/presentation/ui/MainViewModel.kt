package com.myapp.presentation.ui

import androidx.lifecycle.viewModelScope
import com.myapp.domain.usecase.AuthUseCase
import com.myapp.presentation.utils.base.BaseAacViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val authUseCase: AuthUseCase) :
    BaseAacViewModel<MainContract.State, MainContract.Effect, MainContract.Event>() {

    override fun initState(): MainContract.State {
        return MainContract.State()
    }

    override fun handleEvents(event: MainContract.Event) {}

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