package com.myapp.presentation.home.ui.slideshow

import android.content.Context
import android.widget.SeekBar
import androidx.lifecycle.LifecycleOwner
import com.myapp.presentation.R
import com.myapp.presentation.databinding.ItemDiaryNumberInputBinding
import com.xwray.groupie.databinding.BindableItem

/**
 * 振り返り_評価画面
 */
class DiaryAssessmentItem(
    private val context: Context,
    private val viewLifecycleOwner: LifecycleOwner,
    private val viewModel: DiaryItemViewModel
) : BindableItem<ItemDiaryNumberInputBinding>() {

    override fun getLayout(): Int = R.layout.item_diary_number_input

    override fun bind(
        binding: ItemDiaryNumberInputBinding,
        position: Int
    ) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.txtSection.text = context.getString(R.string.section2_1)
        binding.txtTitle.text = context.getString(R.string.title_item_assessment) // 次へボタン

        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                viewModel.changeText(progress.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }
}
