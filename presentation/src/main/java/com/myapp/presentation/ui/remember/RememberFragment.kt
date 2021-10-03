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
import com.myapp.presentation.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

/**
 * 設定画面
 */
@AndroidEntryPoint
class RememberFragment : BaseFragment() {

    private var _binding: FragmentRemenberBinding? = null
    private val binding get() = _binding!!

    private val args: RememberFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: RememberViewModel.Factory
    private val viewModel: RememberViewModel by viewModels {
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

    @ExperimentalCoroutinesApi
    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
