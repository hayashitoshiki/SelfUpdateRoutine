package com.myapp.presentation.ui.diary

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.myapp.presentation.R
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 振り返り_宣言画面
 */
class FfsStatementFragment : DiaryBaseFragment() {

    override val viewModel: FfsStatementViewModel by viewModel()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtSection.text = requireContext().getString(R.string.section1_4)
        binding.txtTitle.text = requireContext().getString(R.string.title_item_declaration)
        binding.btnOk.setOnClickListener {
            findNavController().navigate(R.id.action_diaryStatementItem_to_diaryConfirmItem)
        }
    }
}
