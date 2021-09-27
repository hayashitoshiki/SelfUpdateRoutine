package com.myapp.presentation.ui.diary

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.myapp.presentation.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * 振り返り_教訓画面
 */
@AndroidEntryPoint
class FfsLearnFragment : DiaryBaseFragment() {

    override val viewModel: FfsLearnViewModel by viewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtSection.text = requireContext().getString(R.string.section1_3)
        binding.txtTitle.text = requireContext().getString(R.string.title_item_learn)
        binding.edtInput.hint = requireContext().getString(R.string.hint_item_learn)
        binding.btnOk.setOnClickListener {
            findNavController().navigate(R.id.action_diaryLessonItem_to_diaryStatementItem)
        }
    }
}
