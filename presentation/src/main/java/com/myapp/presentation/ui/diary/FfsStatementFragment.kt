package com.myapp.presentation.ui.diary

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.myapp.presentation.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * 振り返り_宣言画面
 */
@AndroidEntryPoint
class FfsStatementFragment : DiaryBaseFragment() {

    override val viewModel: FfsStatementViewModel by viewModels()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtSection.text = requireContext().getString(R.string.section1_4)
        binding.txtTitle.text = requireContext().getString(R.string.title_item_declaration)
        binding.edtInput.hint = requireContext().getString(R.string.hint_item_statement)
        binding.btnOk.setOnClickListener {
            findNavController().navigate(R.id.action_diaryStatementItem_to_diaryConfirmItem)
        }
    }
}
