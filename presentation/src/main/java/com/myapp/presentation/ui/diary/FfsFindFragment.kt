package com.myapp.presentation.ui.diary

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.myapp.presentation.R
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 振り返り_発見画面
 */
class FfsFindFragment : DiaryBaseFragment() {

    override val viewModel: FfsFindViewModel by viewModel()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtSection.text = requireContext().getString(R.string.section1_2)
        binding.txtTitle.text = requireContext().getString(R.string.title_item_discover)
        binding.btnOk.setOnClickListener {
            findNavController().navigate(R.id.action_diaryFindItem_to_diaryLessonItem)
        }
    }

}
