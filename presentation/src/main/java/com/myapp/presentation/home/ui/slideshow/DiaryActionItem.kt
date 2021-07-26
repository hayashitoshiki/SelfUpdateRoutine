package com.myapp.presentation.home.ui.slideshow

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.myapp.presentation.R
import com.myapp.presentation.databinding.ItemDiaryTextInputBinding
import com.xwray.groupie.databinding.BindableItem

/**
 * 振り返り_改善案画面
 */
class DiaryActionItem(
    private val context: Context,
    private val viewLifecycleOwner: LifecycleOwner,
    private val viewModel: DiaryItemViewModel
) : BindableItem<ItemDiaryTextInputBinding>() {

    override fun getLayout(): Int = R.layout.item_diary_text_input

    override fun bind(
        binding: ItemDiaryTextInputBinding,
        position: Int
    ) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.txtSection.text = context.getString(R.string.section2_3)
        binding.txtTitle.text = context.getString(R.string.title_item_action)
    }
}
