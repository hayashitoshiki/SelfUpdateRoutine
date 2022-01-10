package com.myapp.presentation.ui.account

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.fragment.app.viewModels
import com.myapp.presentation.R
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.NavHostController
import com.myapp.presentation.utils.component.PrimaryColorButton
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import timber.log.Timber


/**
 * アカウント管理画面
 *
 */
@AndroidEntryPoint
class AccountFragment : Fragment() {

    private val viewModel: AccountViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val state = viewModel.state.value
                LaunchedEffect(true) {
                    viewModel.setEvent(AccountContract.Event.OnViewCreated)
                    viewModel.effect.onEach { effect ->
                        when (effect) {
                            is AccountContract.Effect.NavigateSignIn -> {
//                                val directions = AccountFragmentDirections.actionNavAccountToNavSignIn()
//                                findNavController().navigate(directions)
                            }
                            is AccountContract.Effect.NavigateSignUp -> {
//                                val directions = AccountFragmentDirections.actionNavAccountToNavSignUp()
//                                findNavController().navigate(directions)
                            }
                            is AccountContract.Effect.ShowError -> { Timber.e(effect.throwable)}
                            is AccountContract.Effect.ShorDeleteConfirmDialog -> { showDeleteConfirmDialog() }
                            is AccountContract.Effect.OnDestroyView -> { }
                        }
                    }.collect()
                }
                AccountScreenContent(viewModel, state)
            }
        }
    }

    // 削除確認ダイアログ表示
    private fun showDeleteConfirmDialog() {
        AlertDialog.Builder(activity)
            .setTitle("！！注意！！")
            .setMessage("アカウントを削除してもよろしいですか？")
            .setPositiveButton("はい") { _, _ ->
                viewModel.setEvent(AccountContract.Event.OnClickDeleteConfirmOkButton)
            }
            .setNegativeButton("いいえ",null)
            .show()
    }
}

/**
 * 振り返り_事実画面
 *
 */
@ExperimentalMaterialApi
@Composable
fun AccountScreen(
    navController: NavHostController,
    viewModel: AccountViewModel
) {
    val state = viewModel.state.value
    AccountScreenContent(viewModel, state)
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