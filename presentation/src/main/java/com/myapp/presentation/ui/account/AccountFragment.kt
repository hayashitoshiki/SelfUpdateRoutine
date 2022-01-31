package com.myapp.presentation.ui.account

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import com.myapp.presentation.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.myapp.presentation.ui.diary.Screens
import com.myapp.presentation.utils.component.PrimaryColorButton
import com.myapp.presentation.utils.component.ShowDeleteConfirmDialog
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

/**
 * アカウント管理画面
 *
 */
@ExperimentalMaterialApi
@Composable
fun AccountScreen(
    navController: NavHostController,
    viewModel: AccountViewModel
) {
    val state = viewModel.state.value
    val isDialog = remember{ mutableStateOf(false) }
    LaunchedEffect(true) {
        viewModel.setEvent(AccountContract.Event.OnViewCreated)
        viewModel.effect.onEach { effect ->
            when (effect) {
                is AccountContract.Effect.NavigateSignIn -> {
                    navController.navigate(Screens.SIGN_IN_SCREEN.route)
                }
                is AccountContract.Effect.NavigateSignUp -> {
                    navController.navigate(Screens.SIGN_UP_SCREEN.route)
                }
                is AccountContract.Effect.ShowError -> { Timber.e(effect.throwable)}
                is AccountContract.Effect.ShorDeleteConfirmDialog -> { isDialog.value = true }
                is AccountContract.Effect.OnDestroyView -> { }
            }
        }.collect()
    }
    AccountScreenContent(viewModel, state)

    if (isDialog.value) {
        ShowDeleteConfirmDialog(
            title = "！！注意！！",
            message = "アカウントを削除してもよろしいですか？",
            onClickPositiveAction = {
                viewModel.setEvent(AccountContract.Event.OnClickDeleteConfirmOkButton)
                isDialog.value = false
            },
            onClickNegativeAction = { isDialog.value = false }
        )
    }
}

/**
 * 設定画面表示用コンテンツ
 *
 */
@Composable
private fun AccountScreenContent(
    viewModel: AccountViewModel,
    state: AccountContract.State
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundColor = Color.Transparent
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (state.isSignIn) {
                PrimaryColorButton(
                    text = stringResource(id = R.string.btn_sign_out),
                    onClick = { viewModel.setEvent(AccountContract.Event.OnClickSignOutButton) }
                )
                PrimaryColorButton(
                    text = stringResource(id = R.string.btn_account_delete),
                    onClick = { viewModel.setEvent(AccountContract.Event.OnClickDeleteConfirmOkButton) },
                    modifier = Modifier.padding(top = 32.dp)
                )
            } else {
                PrimaryColorButton(
                    text = stringResource(id = R.string.btn_sign_in),
                    onClick = { viewModel.setEvent(AccountContract.Event.OnClickSignInButton) }
                )
                PrimaryColorButton(
                    text = stringResource(id = R.string.btn_sign_up),
                    onClick = { viewModel.setEvent(AccountContract.Event.OnClickSignUpButton) },
                    modifier = Modifier.padding(top = 32.dp)
                )
            }
        }
    }
}
