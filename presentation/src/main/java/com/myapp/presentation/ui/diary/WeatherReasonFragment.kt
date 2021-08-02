package com.myapp.presentation.ui.diary

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.myapp.presentation.R
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 振り返り_理由画面
 */
class WeatherReasonFragment : DiaryBaseFragment() {

    override val viewModel: WeatherReasonViewModel by viewModel()

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.txtSection.text = requireContext().getString(R.string.section2_2)
        binding.txtTitle.text = requireContext().getString(R.string.title_item_reason)
        binding.edtInput.hint = requireContext().getString(R.string.hint_item_reason)
        binding.btnOk.setOnClickListener {
            findNavController().navigate(R.id.action_diaryReasonItem_to_diaryActionItem)
        }
    }

}
