package com.myapp.presentation.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.myapp.presentation.R
import com.myapp.presentation.databinding.FragmentAccountBinding
import com.myapp.presentation.utils.base.BaseAacFragment

class AccountFragment : BaseAacFragment<AccountContract.State, AccountContract.Effect, AccountContract.Event>() {

    override val viewModel: AccountViewModel  by viewModels()

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

    override fun setEvent() {
        binding.btnSignIn.setOnClickListener { viewModel.setEvent(AccountContract.Event.OnClickSignInButton) }
        binding.btnSignOut.setOnClickListener { viewModel.setEvent(AccountContract.Event.OnClickSignOutButton) }
        binding.btnSignUp.setOnClickListener { viewModel.setEvent(AccountContract.Event.OnClickSignUpButton) }
        binding.btnDelete.setOnClickListener { viewModel.setEvent(AccountContract.Event.OnClickDeleteButton) }
    }

    override fun setEffect(effect: AccountContract.Effect) = when(effect) {
        is AccountContract.Effect.NavigateDelete -> TODO()
        is AccountContract.Effect.NavigateSignIn -> {
            val directions = AccountFragmentDirections.actionNavAccountToNavSignIn()
            findNavController().navigate(directions)
        }
        is AccountContract.Effect.NavigateSignOut -> TODO()
        is AccountContract.Effect.NavigateSignUp -> {
            val directions = AccountFragmentDirections.actionNavAccountToNavSignUp()
            findNavController().navigate(directions)
        }
        is AccountContract.Effect.ShowError -> TODO()
        is AccountContract.Effect.OnDestroyView -> { }
    }

    override fun changedState(state: AccountContract.State) {
        if (state.isSignIn) {
            binding.btnSignIn.visibility = View.GONE
            binding.btnSignUp.visibility = View.GONE
            binding.btnSignOut.visibility = View.VISIBLE
            binding.btnDelete.visibility = View.VISIBLE
        } else {
            binding.btnSignIn.visibility = View.VISIBLE
            binding.btnSignUp.visibility = View.VISIBLE
            binding.btnSignOut.visibility = View.GONE
            binding.btnDelete.visibility = View.GONE
        }
    }

    // 画面破棄（初期化）処理
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}