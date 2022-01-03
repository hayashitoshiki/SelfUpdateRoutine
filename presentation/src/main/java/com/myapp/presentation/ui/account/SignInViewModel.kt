package com.myapp.presentation.ui.account

import androidx.lifecycle.viewModelScope
import com.myapp.domain.dto.SignInDto
import com.myapp.domain.usecase.AuthUseCase
import com.myapp.presentation.ui.MainDispatcher
import com.myapp.presentation.ui.MainDispatcherContract
import com.myapp.presentation.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ログイン画面　画面ロジック
 *
 */
@HiltViewModel
class SignInViewModel @Inject constructor(private val authUseCase: AuthUseCase) :
    BaseViewModel<SignInContract.State, SignInContract.Effect, SignInContract.Event>() {

    override fun initState(): SignInContract.State {
        return SignInContract.State()
    }

    override fun handleEvents(event: SignInContract.Event) = when(event) {
        is SignInContract.Event.OnChangeEmail -> changeEmail(event.email)
        is SignInContract.Event.OnChangePassword -> changePassword(event.password)
        is SignInContract.Event.OnClickSignInButton -> signIn()
        is SignInContract.Event.OnDestroyView -> { }
    }

    /**
     * メールアドレス変更
     *
     * @param email メールアドレス
     */
    private fun changeEmail(email: String) {
        setState { copy(emailText = email) }
        changeSignInEnable()
    }

    /**
     * パスワード変更
     *
     * @param password パスワード
     */
    private fun changePassword(password: String) {
        setState { copy(passwordText = password) }
        changeSignInEnable()
    }

    /**
     * ログインボタン活性・非活性変更
     *
     */
    private fun changeSignInEnable() {
        val isSignInEnable = state.value.emailText.isNotEmpty() && state.value.passwordText.isNotEmpty()
        setState { copy(isSignInEnable = isSignInEnable) }
    }

    /**
     * ログイン処理
     *
     */
    private fun signIn() {
        val authInputDto = SignInDto(state.value.emailText, state.value.passwordText)
        viewModelScope.launch {
            setEffect { SignInContract.Effect.ShorProgressBer(true) }
            runCatching { authUseCase.signIn(authInputDto) }
                .onSuccess {
                    setEffect { SignInContract.Effect.ShorProgressBer(false) }
                    setEffect { SignInContract.Effect.NavigateHome }
                    MainDispatcher.setActions(MainDispatcherContract.Action.AuthUpdate)
                }
                .onFailure {
                    setEffect { SignInContract.Effect.ShorProgressBer(false) }
                    setEffect{ SignInContract.Effect.ShowError(it) }
                }
        }
    }

}