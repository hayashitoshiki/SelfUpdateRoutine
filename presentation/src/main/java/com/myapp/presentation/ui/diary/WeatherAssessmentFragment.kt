package com.myapp.presentation.ui.diary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.myapp.presentation.R
import com.myapp.presentation.databinding.ItemDiaryNumberInputBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * 振り返り_評価画面
 */
class WeatherAssessmentFragment : Fragment() {

    private lateinit var binding: ItemDiaryNumberInputBinding

    private val viewModel: WeatherAssessmentViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.item_diary_number_input, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.txtSection.text = requireContext().getString(R.string.section2_1)
        binding.txtTitle.text = requireContext().getString(R.string.title_item_assessment)
        binding.btnOk.setOnClickListener {
            findNavController().navigate(R.id.action_diaryAssessmentItem_to_diaryReasonItem)
        }
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                viewModel.changeProgress(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
        return binding.root
    }

}
