package com.myapp.presentation.ui.account

import android.widget.Toast
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
 * ログイン画面
 *
 */
@ExperimentalMaterialApi
@Composable
fun SignInScreen(
    navController: NavHostController,
    viewModel: SignInViewModel
) {
    val state = viewModel.state.value
    val event: (SignInContract.Event) -> Unit = { viewModel.setEvent(it) }
    val context = LocalContext.current
    val isProgressIndicator = remember{ mutableStateOf(false) }
    LaunchedEffect(true) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                is SignInContract.Effect.NavigateHome -> {
                    navController.popBackStack()
                    navController.popBackStack()
                }
                is SignInContract.Effect.OnDestroyView -> { }
                is SignInContract.Effect.ShowError -> {
                    Timber.tag(this.javaClass.simpleName).d(effect.throwable)
                    Toast.makeText(context, "ログインに失敗しました", Toast.LENGTH_SHORT).show()
                }
                is SignInContract.Effect.ShorProgressBer -> isProgressIndicator.value = effect.value
            }
        }.collect()
    }
    SignInScreenContent(event, state)
    if (isProgressIndicator.value) {
        ProgressBar()
    }
}

/**
 * ログイン画面表示用コンテンツ
 *
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun SignInScreenContent(
    event: (SignInContract.Event) -> Unit,
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
                .padding(start = 32.dp, end = 32.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .pointerInput(Unit) { detectTapGestures(onTap = { focusManager.clearFocus() }) },
        ) {
            OutlinedTextField(
                value = state.emailText,
                singleLine = true,
                onValueChange = { event(SignInContract.Event.OnChangeEmail(it)) },
                label = { Text(stringResource(id = R.string.title_sub_email)) },
                modifier = Modifier.padding(top = 8.dp).fillMaxWidth()
            )
            OutlinedTextField(
                value = state.passwordText,
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                onValueChange = { event(SignInContract.Event.OnChangePassword(it)) },
                label = { Text(stringResource(id = R.string.title_sub_password)) },
                modifier = Modifier.padding(top = 8.dp).fillMaxWidth()
            )
            PrimaryColorButton(
                text = stringResource(id = R.string.btn_sign_in),
                enabled = state.isSignInEnable,
                onClick = { event(SignInContract.Event.OnClickSignInButton) },
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}
