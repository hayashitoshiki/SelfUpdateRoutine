package com.myapp.presentation.utils.base

import com.myapp.presentation.R
import com.myapp.presentation.databinding.ItemInputTextBinding
import com.xwray.groupie.databinding.BindableItem

/**
 * リサイクルビュー_テキスト入力用アイテム
 *
 * @property viewModelBase 画面ロジック
 */
abstract class BaseInputTextItem(private val viewModelBase: BaseInputTextItemViewModel) : BindableItem<ItemInputTextBinding>() {

    override fun getLayout(): Int = R.layout.item_input_text

    override fun bind(
        binding: ItemInputTextBinding,
        position: Int
    ) {
        binding.viewModel = viewModelBase
    }
}
