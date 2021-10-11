package com.myapp.presentation.utils.base

import com.myapp.presentation.R
import com.myapp.presentation.databinding.ItemInputTextBinding
import com.xwray.groupie.databinding.BindableItem

/**
 * リサイクルビュー_テキスト入力用アイテム
 *
 * @property viewModel 画面ロジック
 */
abstract class BaseInputTextItem(private val viewModel: BaseInputTextItemViewModel) : BindableItem<ItemInputTextBinding>() {

    override fun getLayout(): Int = R.layout.item_input_text

    override fun bind(
        binding: ItemInputTextBinding,
        position: Int
    ) {
        binding.viewModel = viewModel
        binding.btnPlus.setOnClickListener { viewModel.setEvent(BaseInputTextItemContract.Event.OnClickPlusButton) }
        binding.btnMinus.setOnClickListener { viewModel.setEvent(BaseInputTextItemContract.Event.OnClickMinusButton) }
    }

    override fun getId(): Long = viewModel.id
}
