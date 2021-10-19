package com.myapp.presentation.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.myapp.presentation.R
import com.myapp.presentation.databinding.FragmentSignInBinding
import com.myapp.presentation.utils.base.BaseAacFragment
import timber.log.Timber

/**
 * ログイン画面
 *
 */
class SignInFragment :
    BaseAacFragment<SignInContract.State, SignInContract.Effect, SignInContract.Event>() {

    override val viewModel: SignInViewModel by viewModels()

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun setEvent() {
        binding.edtEmail.doAfterTextChanged { viewModel.setEvent(SignInContract.Event.OnChangeEmail(it.toString())) }
        binding.edtPassword.doAfterTextChanged { viewModel.setEvent(SignInContract.Event.OnChangePassword(it.toString())) }
        binding.btnSignIn.setOnClickListener { viewModel.setEvent(SignInContract.Event.OnClickSignInButton) }
    }

    override fun setEffect(effect: SignInContract.Effect) = when(effect){
        is SignInContract.Effect.NavigateHome -> backHome()
        is SignInContract.Effect.OnDestroyView -> { }
        is SignInContract.Effect.ShowError -> Timber.tag(this.javaClass.simpleName).d(effect.throwable)
    }

    override fun changedState(state: SignInContract.State) {
        viewModel.cashState.let{ cash ->
            if (cash == null) {
                binding.edtEmail.setText(state.emailText)
                binding.edtPassword.setText(state.passwordText)
            }
            if (cash == null || state.isSignInEnable != cash.isSignInEnable) {
                binding.btnSignIn.isEnabled = state.isSignInEnable
            }
        }
    }

    // ホーム画面遷移
    private fun backHome() {
        findNavController().popBackStack()
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}