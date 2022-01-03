package com.myapp.presentation.ui.account

import androidx.lifecycle.viewModelScope
import com.myapp.domain.dto.AuthInputDto
import com.myapp.domain.model.value.Email
import com.myapp.domain.model.value.Password
import com.myapp.domain.usecase.AuthUseCase
import com.myapp.presentation.ui.MainDispatcher
import com.myapp.presentation.ui.MainDispatcherContract
import com.myapp.presentation.utils.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * アカウント作成画面　画面ロジック
 *
 */
@HiltViewModel
class SignUpViewModel @Inject constructor(private val authUseCase: AuthUseCase) :
    BaseViewModel<SignUpContract.State, SignUpContract.Effect, SignUpContract.Event>() {

    override fun initState(): SignUpContract.State {
        return SignUpContract.State()
    }

    override fun handleEvents(event: SignUpContract.Event) = when(event) {
        is SignUpContract.Event.OnChangeEmail1 -> changeEmail1(event.email)
        is SignUpContract.Event.OnChangeEmail2 -> changeEmail2(event.email)
        is SignUpContract.Event.OnChangePassword1 -> changePassword1(event.password)
        is SignUpContract.Event.OnChangePassword2 -> changePassword2(event.password)
        is SignUpContract.Event.OnClickSignUpButton -> signUp()
        is SignUpContract.Event.OnDestroyView -> {}
    }

    /**
     * メールアドレス変更
     *
     * @param email メールアドレス
     */
    private fun changeEmail1(email: String) {
        setState { copy(email1Text = email) }
        changeSignInEnable()
    }

    /**
     * 確認用メールアドレス変更
     *
     * @param email 確認用メールアドレス
     */
    private fun changeEmail2(email: String) {
        setState { copy(email2Text = email) }
        changeSignInEnable()
    }

    /**
     * パスワード変更
     *
     * @param password パスワード
     */
    private fun changePassword1(password: String) {
        setState { copy(password1Text = password) }
        changeSignInEnable()
    }

    /**
     * 確認用パスワード変更
     *
     * @param password パスワード
     */
    private fun changePassword2(password: String) {
        setState { copy(password2Text = password) }
        changeSignInEnable()
    }

    /**
     * ログインボタン活性・非活性変更
     *
     */
    private fun changeSignInEnable() {
        val isSignUpEnable = when {
            state.value.email1Text.isEmpty() -> false
            state.value.email2Text.isEmpty() -> false
            !Email.check(state.value.email1Text) -> false
            state.value.email1Text != state.value.email2Text -> false
            state.value.password1Text.isEmpty() -> false
            state.value.password2Text.isEmpty() -> false
            !Password.check(state.value.password1Text) -> false
            state.value.password1Text != state.value.password2Text -> false
            else -> true
        }
        setState { copy(isSignUpEnable = isSignUpEnable) }
    }

    /**
     * アカウント登録処理
     *
     */
    private fun signUp() {
        val state = state.value ?: return
        viewModelScope.launch {
            runCatching {
                val email = Email(state.email1Text)
                val password = Password(state.password1Text)
                val authInputDto = AuthInputDto(email, password)
                setEffect { SignUpContract.Effect.ShorProgressBer(true) }
                authUseCase.signUp(authInputDto)
            }
                .onSuccess {
                    setEffect { SignUpContract.Effect.ShorProgressBer(false) }
                    setEffect { SignUpContract.Effect.NavigateHome }
                    MainDispatcher.setActions(MainDispatcherContract.Action.AuthUpdate)
                }
                .onFailure {
                    setEffect { SignUpContract.Effect.ShorProgressBer(false) }
                    setEffect { SignUpContract.Effect.ShowError(it) }
                }
        }
    }

}