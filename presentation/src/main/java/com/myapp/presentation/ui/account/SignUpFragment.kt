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
import com.myapp.presentation.databinding.FragmentSignUpBinding
import com.myapp.presentation.utils.base.BaseAacFragment
import timber.log.Timber

/**
 * アカウント作成画面
 *
 */
class SignUpFragment :
    BaseAacFragment<SignUpContract.State, SignUpContract.Effect, SignUpContract.Event>() {

    override val viewModel: SignUpViewModel by viewModels()

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun setEvent() {
        binding.edtEmail1.doAfterTextChanged { viewModel.setEvent(SignUpContract.Event.OnChangeEmail1(it.toString())) }
        binding.edtEmail2.doAfterTextChanged { viewModel.setEvent(SignUpContract.Event.OnChangeEmail2(it.toString())) }
        binding.edtPassword1.doAfterTextChanged { viewModel.setEvent(SignUpContract.Event.OnChangePassword1(it.toString())) }
        binding.edtPassword2.doAfterTextChanged { viewModel.setEvent(SignUpContract.Event.OnChangePassword2(it.toString())) }
        binding.btnSignIn.setOnClickListener { viewModel.setEvent(SignUpContract.Event.OnClickSignInButton) }
    }

    override fun setEffect(effect: SignUpContract.Effect) = when(effect){
        is SignUpContract.Effect.NavigateHome -> backHome()
        is SignUpContract.Effect.OnDestroyView -> { }
        is SignUpContract.Effect.ShowError -> Timber.tag(this.javaClass.simpleName).d(effect.throwable)
    }

    override fun changedState(state: SignUpContract.State) {
        viewModel.cashState.let{ cash ->
            if (cash == null) {
                binding.edtEmail1.setText(state.email1Text)
                binding.edtEmail2.setText(state.email2Text)
                binding.edtPassword1.setText(state.password1Text)
                binding.edtPassword2.setText(state.password2Text)
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