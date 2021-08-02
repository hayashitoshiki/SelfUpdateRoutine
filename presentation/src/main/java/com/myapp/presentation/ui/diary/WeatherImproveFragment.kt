package com.myapp.presentation.ui.diary

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.myapp.presentation.R
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 振り返り_改善案画面
 */
class WeatherImproveFragment : DiaryBaseFragment() {

    override val viewModel: WeatherImproveViewModel by viewModel()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtSection.text = requireContext().getString(R.string.section2_3)
        binding.txtTitle.text = requireContext().getString(R.string.title_item_action)
        binding.edtInput.hint = requireContext().getString(R.string.hint_item_improve)
        binding.btnOk.setOnClickListener {
            findNavController().navigate(R.id.action_diaryActionItem_to_diaryResultItem)
        }
    }

}
