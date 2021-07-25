package com.myapp.presentation.home.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.myapp.presentation.R
import com.myapp.presentation.databinding.FragmentSlideshowBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.ViewHolder
import org.koin.androidx.viewmodel.ext.android.viewModel


class SlideshowFragment : Fragment() {

    private lateinit var binding: FragmentSlideshowBinding
    private val viewModel: SlideshowViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_slideshow, container, false)
        binding.lifecycleOwner = viewLifecycleOwner //        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager2.let {
            val adapter = GroupAdapter<ViewHolder>()
            val listener = OnItemClickListener { _, _ -> requireActivity().finish() }

            val factViewModel = DiaryFactItemViewModel()
            val discoverViewModel = DiaryDiscoverItemViewModel()
            val lessonViewModel = DiaryLessonItemViewModel()
            val statementViewModel = DiaryStatementItemViewModel()
            val assessmentViewModel = DiaryAssessmentViewModel()
            val reasonViewModel = DiaryReasonItemViewModel()
            val actionViewModel = DiaryActionItemViewModel()
            val list = arrayListOf(
                DiaryFactItem(requireContext(), viewLifecycleOwner, factViewModel),
                DiaryDiscoverItem(requireContext(), viewLifecycleOwner, discoverViewModel),
                DiaryLessonItem(requireContext(), viewLifecycleOwner, lessonViewModel),
                DiaryStatementItem(requireContext(), viewLifecycleOwner, statementViewModel),
                DiaryConfirmItem(viewLifecycleOwner, viewModel),
                DiaryAssessmentItem(requireContext(), viewLifecycleOwner, assessmentViewModel),
                DiaryReasonItem(requireContext(), viewLifecycleOwner, reasonViewModel),
                DiaryActionItem(requireContext(), viewLifecycleOwner, actionViewModel),
                DiaryResultItem(listener, viewLifecycleOwner, viewModel)
            )
            adapter.update(list)
            it.adapter = adapter
            TabLayoutMediator(binding.tabs, it) { tab, position -> // TODO : 非活性時の制御
                if (position == 3) {
                    tab.text = ""
                } else {
                    tab.text = "●"
                }
            }.attach()
        }
    }

}