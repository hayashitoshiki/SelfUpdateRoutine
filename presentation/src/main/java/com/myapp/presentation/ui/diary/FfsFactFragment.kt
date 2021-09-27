package com.myapp.presentation.ui.diary

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.myapp.presentation.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * 振り返り_事実画面
 */
@AndroidEntryPoint
class FfsFactFragment : DiaryBaseFragment() {

    override val viewModel: FfsFactViewModel by viewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtSection.text = requireContext().getString(R.string.section1_1)
        binding.txtTitle.text = requireContext().getString(R.string.title_item_fact)
        binding.edtInput.hint = requireContext().getString(R.string.hint_item_fact)
        binding.btnOk.setOnClickListener {
            findNavController().navigate(R.id.action_diaryFactItem_to_diaryFindItem)
        }
    }
}
