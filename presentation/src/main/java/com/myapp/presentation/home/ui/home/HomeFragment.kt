package com.myapp.presentation.home.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.myapp.presentation.databinding.FragmentHomeBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)


        // TODO : 仮表示
        homeViewModel.report.observe(viewLifecycleOwner, {
            val ffsReport = it.ffsReport
            val emotionsReport = it.emotionsReport

            binding.textView1.text = ffsReport.factComment
            binding.textView2.text = ffsReport.findComment
            binding.textView3.text = ffsReport.learnComment
            binding.textView4.text = ffsReport.statementComment
            binding.textView5.text = emotionsReport.heartScore.data.toString()
            binding.textView6.text = emotionsReport.reasonComment
            binding.textView7.text = emotionsReport.improveComment
            binding.textView8.text = ffsReport.dataTime.toString()
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}