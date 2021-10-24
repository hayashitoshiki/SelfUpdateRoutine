package com.myapp.presentation.ui.account

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.myapp.presentation.R
import com.myapp.presentation.databinding.FragmentAccountBinding
import com.myapp.presentation.utils.base.BaseAacFragment
import dagger.hilt.android.AndroidEntryPoint
import android.content.DialogInterface
import timber.log.Timber


/**
 * アカウント管理画面
 *
 */
@AndroidEntryPoint
class AccountFragment : BaseAacFragment<AccountContract.State, AccountContract.Effect, AccountContract.Event>() {

    override val viewModel: AccountViewModel by viewModels()

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setEvent(AccountContract.Event.OnViewCreated)
    }

    override fun setEvent() {
        binding.btnSignIn.setOnClickListener { viewModel.setEvent(AccountContract.Event.OnClickSignInButton) }
        binding.btnSignOut.setOnClickListener { viewModel.setEvent(AccountContract.Event.OnClickSignOutButton) }
        binding.btnSignUp.setOnClickListener { viewModel.setEvent(AccountContract.Event.OnClickSignUpButton) }
        binding.btnDelete.setOnClickListener { viewModel.setEvent(AccountContract.Event.OnClickDeleteButton) }
    }

    override fun setEffect(effect: AccountContract.Effect) = when(effect) {
        is AccountContract.Effect.NavigateSignIn -> {
            val directions = AccountFragmentDirections.actionNavAccountToNavSignIn()
            findNavController().navigate(directions)
        }
        is AccountContract.Effect.NavigateSignUp -> {
            val directions = AccountFragmentDirections.actionNavAccountToNavSignUp()
            findNavController().navigate(directions)
        }
        is AccountContract.Effect.ShowError -> { Timber.e(effect.throwable)}
        is AccountContract.Effect.ShorDeleteConfirmDialog -> { showDeleteConfirmDialog() }
        is AccountContract.Effect.OnDestroyView -> { }
    }

    override fun changedState(state: AccountContract.State) {
        binding.btnSignIn.isVisible = !state.isSignIn
        binding.btnSignUp.isVisible = !state.isSignIn
        binding.btnSignOut.isVisible = state.isSignIn
        binding.btnDelete.isVisible = state.isSignIn
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

    // 画面破棄（初期化）処理
    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.setEvent(AccountContract.Event.OnDestroyView)
        _binding = null
    }
}