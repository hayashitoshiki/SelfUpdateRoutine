package com.myapp.presentation.ui.account

import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.RelocationRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.insets.LocalWindowInsets
import com.myapp.presentation.R
import com.myapp.presentation.utils.component.PrimaryColorButton
import com.myapp.presentation.utils.component.ProgressBar
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

/**
 * アカウント作成画面
 *
 */
@ExperimentalMaterialApi
@Composable
fun SignUpScreen(
    navController: NavHostController,
    viewModel: SignUpViewModel
) {
    val context = LocalContext.current
    val state = viewModel.state.value
    val isProgressIndicator = remember{ mutableStateOf(false)}
    LaunchedEffect(true) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                is SignUpContract.Effect.NavigateHome -> {
                    navController.popBackStack()
                    navController.popBackStack()
                }
                is SignUpContract.Effect.OnDestroyView -> { }
                is SignUpContract.Effect.ShowError -> {
                    Timber.tag(this.javaClass.simpleName).d(effect.throwable)
                    Toast.makeText(context, "アカウントの作成に失敗しました。", Toast.LENGTH_SHORT).show()
                }
                is SignUpContract.Effect.ShorProgressBer -> isProgressIndicator.value = effect.value
            }
        }.collect()
    }
    SignUpScreenContent(viewModel, state)
    if (isProgressIndicator.value) {
        ProgressBar()
    }
}

/**
 * 設定画面表示用コンテンツ
 *
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SignUpScreenContent(
    viewModel: SignUpViewModel,
    state: SignUpContract.State
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
                value = state.email1Text,
                singleLine = true,
                onValueChange = { viewModel.setEvent(SignUpContract.Event.OnChangeEmail1(it)) },
                label = { Text(stringResource(id = R.string.title_sub_email)) },
                modifier = Modifier.padding(top = 8.dp)
            )
            OutlinedTextField(
                value = state.email2Text,
                singleLine = true,
                onValueChange = { viewModel.setEvent(SignUpContract.Event.OnChangeEmail2(it)) },
                label = { Text(stringResource(id = R.string.title_sub_email_confirm)) },
                modifier = Modifier.padding(top = 8.dp)
            )
            OutlinedTextField(
                value = state.password1Text,
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                onValueChange = { viewModel.setEvent(SignUpContract.Event.OnChangePassword1(it)) },
                label = { Text(stringResource(id = R.string.title_sub_password)) },
                modifier = Modifier.padding(top = 8.dp)
            )
            OutlinedTextField(
                value = state.password2Text,
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                onValueChange = { viewModel.setEvent(SignUpContract.Event.OnChangePassword2(it)) },
                label = { Text(stringResource(id = R.string.title_sub_password_confirm)) },
                modifier = Modifier.padding(top = 8.dp)
            )
            PrimaryColorButton(
                text = stringResource(id = R.string.btn_sign_up),
                enable = state.isSignUpEnable,
                onClick = { viewModel.setEvent(SignUpContract.Event.OnClickSignUpButton) },
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
