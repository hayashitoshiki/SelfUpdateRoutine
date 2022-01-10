package com.myapp.presentation.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.RelocationRequester
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavHostController
import androidx.navigation.fragment.findNavController
import com.google.accompanist.insets.LocalWindowInsets
import com.myapp.presentation.R
import com.myapp.presentation.utils.component.PrimaryColorButton
import com.myapp.presentation.utils.component.ProgressBar
import dagger.hilt.android.AndroidEntryPoint
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

/**
 * ログイン画面
 *
 */
@AndroidEntryPoint
class SignInFragment : Fragment() {

    private val viewModel: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val state = viewModel.state.value
                val isProgressIndicator = remember{ mutableStateOf(false) }
                LaunchedEffect(true) {
                    viewModel.effect.onEach { effect ->
                        when (effect) {
                            is SignInContract.Effect.NavigateHome -> backHome()
                            is SignInContract.Effect.OnDestroyView -> { }
                            is SignInContract.Effect.ShowError -> shoeErrorToast(effect.throwable)
                            is SignInContract.Effect.ShorProgressBer -> isProgressIndicator.value = effect.value
                        }
                    }.collect()
                }
                SignInScreenContent(viewModel, state)
                if (isProgressIndicator.value) {
                    ProgressBar()
                }
            }
        }
    }

    // エラートースト表示
    private fun shoeErrorToast(throwable: Throwable) {
        Timber.tag(this.javaClass.simpleName).d(throwable)
        Toasty.error(requireContext(), "ログインに失敗しました。", Toast.LENGTH_SHORT, true).show()
    }

    // ホーム画面遷移
    private fun backHome() {
        findNavController().popBackStack()
        findNavController().popBackStack()
    }
}


/**
 * 振り返り_事実画面
 *
 */
@ExperimentalMaterialApi
@Composable
fun SignInScreen(
    navController: NavHostController,
    viewModel: SignInViewModel
) {
    val state = viewModel.state.value
    SignInScreenContent(viewModel, state)
}

/**
 * ログイン画面表示用コンテンツ
 *
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SignInScreenContent(
    viewModel: SignInViewModel,
    state: SignInContract.State
) {
    val focusManager = LocalFocusManager.current
    val relocationRequester = remember { RelocationRequester() }
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()
    val ime = LocalWindowInsets.current.ime
    if (ime.isVisible && !ime.animationInProgress && isFocused) {
        LaunchedEffect(Unit) {
            relocationRequester.bringIntoView()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Color.Transparent
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) },
        ) {
            OutlinedTextField(
                value = state.emailText,
                singleLine = true,
                onValueChange = { viewModel.setEvent(SignInContract.Event.OnChangeEmail(it)) },
                label = { Text(stringResource(id = R.string.title_sub_email)) },
                modifier = Modifier.padding(top = 8.dp)
            )
            OutlinedTextField(
                value = state.passwordText,
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                onValueChange = { viewModel.setEvent(SignInContract.Event.OnChangePassword(it)) },
                label = { Text(stringResource(id = R.string.title_sub_password)) },
                modifier = Modifier.padding(top = 8.dp)
            )
            PrimaryColorButton(
                text = stringResource(id = R.string.btn_sign_in),
                enable = state.isSignInEnable,
                onClick = { viewModel.setEvent(SignInContract.Event.OnClickSignInButton) },
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
