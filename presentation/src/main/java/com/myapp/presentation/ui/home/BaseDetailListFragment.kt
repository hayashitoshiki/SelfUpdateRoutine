package com.myapp.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.myapp.presentation.databinding.FragmentReportDetailListBaseBinding
import com.myapp.presentation.utils.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * 一覧画面基盤
 *
 */
@AndroidEntryPoint
abstract class BaseDetailListFragment : BaseFragment() {

    protected var _binding: FragmentReportDetailListBaseBinding? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReportDetailListBaseBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
