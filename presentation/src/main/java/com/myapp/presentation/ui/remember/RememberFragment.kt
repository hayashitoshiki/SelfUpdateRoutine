package com.myapp.presentation.ui.remember

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.myapp.presentation.R
import com.myapp.presentation.databinding.FragmentRemenberBinding
import com.myapp.presentation.utils.base.BaseAacFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * 過去の振り返り詳細画面
 *
 */
@AndroidEntryPoint
class RememberFragment : BaseAacFragment<RememberContract.State, RememberContract.Effect, RememberContract.Event>() {

    private var _binding: FragmentRemenberBinding? = null
    private val binding get() = _binding!!

    private val args: RememberFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: RememberViewModel.Factory
    override val viewModel: RememberViewModel by viewModels {
        RememberViewModel.provideFactory(viewModelFactory, args.report)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_remenber, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun setEvent() { }

    override fun setEffect(effect: RememberContract.Effect) {}

    override fun changedState(state: RememberContract.State) {}
}
