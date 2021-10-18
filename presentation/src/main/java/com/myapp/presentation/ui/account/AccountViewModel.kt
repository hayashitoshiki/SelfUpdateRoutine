package com.myapp.presentation.ui.account

import com.myapp.presentation.utils.base.BaseAacViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * アカウント画面 画面ロジック
 *
 */
@HiltViewModel
class AccountViewModel @Inject constructor() :
    BaseAacViewModel<AccountContract.State, AccountContract.Effect, AccountContract.Event>() {

    override fun initState(): AccountContract.State {
       return AccountContract.State()
    }

    override fun handleEvents(event: AccountContract.Event) = when(event) {
        is AccountContract.Event.OnClickDeleteButton -> setEffect{ AccountContract.Effect.NavigateDelete }
        is AccountContract.Event.OnClickSignInButton -> setEffect{ AccountContract.Effect.NavigateSignIn }
        is AccountContract.Event.OnClickSignOutButton -> setEffect{ AccountContract.Effect.NavigateSignOut }
        is AccountContract.Event.OnClickSignUpButton -> setEffect{ AccountContract.Effect.NavigateSignUp }
        is AccountContract.Event.OnDestroyView -> setEffect{ AccountContract.Effect.OnDestroyView }
    }
}