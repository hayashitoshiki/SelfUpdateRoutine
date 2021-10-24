package com.myapp.presentation.ui.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.myapp.presentation.R
import com.myapp.presentation.databinding.FragmentSignUpBinding
import com.myapp.presentation.utils.base.BaseAacFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/**
 * アカウント作成画面
 *
 */
@AndroidEntryPoint
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
        binding.btnSignIn.setOnClickListener { viewModel.setEvent(SignUpContract.Event.OnClickSignUpButton) }
    }

    override fun setEffect(effect: SignUpContract.Effect) = when(effect){
        is SignUpContract.Effect.NavigateHome -> backHome()
        is SignUpContract.Effect.OnDestroyView -> { }
        is SignUpContract.Effect.ShowError -> Timber.tag(this.javaClass.simpleName).d(effect.throwable)
        is SignUpContract.Effect.ShorProgressBer -> showProgressBar(effect.value)
    }

    override fun changedState(state: SignUpContract.State) {
        viewModel.cashState.let{ cash ->
            if (cash == null) {
                binding.edtEmail1.setText(state.email1Text)
                binding.edtEmail2.setText(state.email2Text)
                binding.edtPassword1.setText(state.password1Text)
                binding.edtPassword2.setText(state.password2Text)
            }
            if (cash == null || state.isSignUpEnable != cash.isSignUpEnable) {
                binding.btnSignIn.isEnabled = state.isSignUpEnable
            }
        }
    }

    // プログレスバー表示制御
    private fun showProgressBar(value: Boolean) {
        binding.edtEmail1.isEnabled = !value
        binding.edtEmail2.isEnabled = !value
        binding.edtPassword1.isEnabled = !value
        binding.edtPassword2.isEnabled = !value
        binding.btnSignIn.isEnabled = !value
        binding.progressBar.isVisible = value
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