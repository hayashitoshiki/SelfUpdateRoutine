package com.myapp.presentation.ui.account

import androidx.lifecycle.viewModelScope
import com.myapp.domain.usecase.AuthUseCase
import com.myapp.presentation.ui.MainDispatcher
import com.myapp.presentation.ui.MainDispatcherContract
import com.myapp.presentation.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * アカウント管理画面 画面ロジック
 *
 */
@HiltViewModel
class AccountViewModel @Inject constructor(private val authUseCase: AuthUseCase) :
    BaseViewModel<AccountContract.State, AccountContract.Effect, AccountContract.Event>() {

    override fun initState(): AccountContract.State {
       return AccountContract.State()
    }

    override fun handleEvents(event: AccountContract.Event) = when(event) {
        is AccountContract.Event.OnViewCreated -> checkAuth()
        is AccountContract.Event.OnClickDeleteButton -> setEffect{ AccountContract.Effect.ShorDeleteConfirmDialog }
        is AccountContract.Event.OnClickDeleteConfirmOkButton -> accountDelete()
        is AccountContract.Event.OnClickSignInButton -> setEffect{ AccountContract.Effect.NavigateSignIn }
        is AccountContract.Event.OnClickSignOutButton -> signOut()
        is AccountContract.Event.OnClickSignUpButton -> setEffect{ AccountContract.Effect.NavigateSignUp }
        is AccountContract.Event.OnDestroyView -> setEffect{ AccountContract.Effect.OnDestroyView }
    }

    /**
     * ログイン済み判定
     *
     */
    private fun checkAuth() {
        val isSignIn = authUseCase.autoAuth()
        setState { copy(isSignIn = isSignIn) }
    }

    /**
     * ログアウト
     *
     */
    private fun signOut() {
        viewModelScope.launch {
            runCatching { authUseCase.signOut() }
                .onSuccess {
                    setState { copy(isSignIn = false) }
                    MainDispatcher.setActions(MainDispatcherContract.Action.AuthUpdate)
                }
                .onFailure { setEffect{ AccountContract.Effect.ShowError(it) } }
        }
    }

    /**
     * アカウント削除
     *
     */
    private fun accountDelete() {
        viewModelScope.launch {
            runCatching { authUseCase.delete() }
                .onSuccess {
                    setState { copy(isSignIn = false) }
                    MainDispatcher.setActions(MainDispatcherContract.Action.AuthUpdate)
                }
                .onFailure { setEffect{ AccountContract.Effect.ShowError(it) } }
        }
    }
}