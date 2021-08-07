package com.myapp.presentation.ui.home

import androidx.lifecycle.LifecycleOwner
import com.myapp.domain.model.entity.Report
import com.myapp.presentation.R
import com.myapp.presentation.databinding.ItemHomeCardDiaryBinding
import com.xwray.groupie.databinding.BindableItem


/**
 * 簡易振り返りカード
 */
class DiaryCardItem(
    private val viewModel: DiaryCardViewModel,
    private val viewLifecycleOwner: LifecycleOwner,
    private val listener: OnDiaryCardItemClickListener
) : BindableItem<ItemHomeCardDiaryBinding>() {

    override fun getLayout(): Int = R.layout.item_home_card_diary

    override fun bind(
        binding: ItemHomeCardDiaryBinding,
        position: Int
    ) {
        binding.viewModel = viewModel // 画像設定
        viewModel.heartScoreImg.observe(viewLifecycleOwner, { binding.customImg.setImg(it) })
        binding.root.setOnClickListener {
            viewModel.report.value?.let {
                listener.onItemClick(it)
            }
        }
    }
}

interface OnDiaryCardItemClickListener {
    fun onItemClick(report: Report)
}
