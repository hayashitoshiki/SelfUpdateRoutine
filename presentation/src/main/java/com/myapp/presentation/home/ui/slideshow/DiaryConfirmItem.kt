package com.myapp.presentation.home.ui.slideshow

import androidx.lifecycle.LifecycleOwner
import com.myapp.presentation.R
import com.myapp.presentation.databinding.ItemDiaryConfirm1Binding
import com.xwray.groupie.databinding.BindableItem

/**
 * 振り返り_前半確認画面
 */
class DiaryConfirmItem(
    private val viewLifecycleOwner: LifecycleOwner,
    private val viewModel: SlideshowViewModel
) : BindableItem<ItemDiaryConfirm1Binding>() {

    override fun getLayout(): Int = R.layout.item_diary_confirm1

    override fun bind(
        binding: ItemDiaryConfirm1Binding,
        position: Int
    ) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

    }
}
