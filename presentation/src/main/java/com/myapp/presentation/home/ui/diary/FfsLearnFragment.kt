package com.myapp.presentation.home.ui.diary

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.myapp.presentation.R
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 振り返り_教訓画面
 */
class FfsLearnFragment : DiaryBaseFragment() {

    override val viewModel: FfsLearnViewModel by viewModel()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtSection.text = requireContext().getString(R.string.section1_3)
        binding.txtTitle.text = requireContext().getString(R.string.title_item_leson)
        binding.btnOk.setOnClickListener {
            findNavController().navigate(R.id.action_diaryLessonItem_to_diaryStatementItem)
        }
    }

}
