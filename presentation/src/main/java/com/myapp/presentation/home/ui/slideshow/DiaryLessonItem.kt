package com.myapp.presentation.home.ui.slideshow

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LifecycleOwner
import com.myapp.presentation.R
import com.myapp.presentation.databinding.ItemDiaryTextInputBinding
import com.xwray.groupie.databinding.BindableItem

/**
 * 振り返り_教訓画面
 */
class DiaryLessonItem(
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
        binding.txtSection.text = context.getString(R.string.section1_3)
        binding.txtTitle.text = context.getString(R.string.title_item_leson) // 次へボタン
        binding.edtInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {
                viewModel.changeText(s.toString())
            }
        })
    }
}
