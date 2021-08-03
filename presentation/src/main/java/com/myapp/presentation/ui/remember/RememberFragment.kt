package com.myapp.presentation.ui.remember

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.myapp.presentation.R
import com.myapp.presentation.databinding.FragmentRemenberBinding
import com.myapp.presentation.utill.BaseFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

/**
 * 設定画面
 */
class RememberFragment : BaseFragment() {

    private lateinit var binding: FragmentRemenberBinding

    private val args: RememberFragmentArgs by navArgs()
    val viewModel: RememberViewModel by inject { parametersOf(args.report) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_remenber, container, false)
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
}