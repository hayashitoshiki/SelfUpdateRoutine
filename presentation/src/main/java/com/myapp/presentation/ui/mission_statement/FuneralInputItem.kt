package com.myapp.presentation.ui.mission_statement

import com.myapp.presentation.databinding.ItemInputTextBinding
import com.myapp.presentation.utill.BaseInputTextItem

/**
 * ミッションステートメント_理想の葬儀リストアイテム
 *
 * @property viewModel 理想の葬儀リストアイテムの画面ロジック
 */
class FuneralInputItem(private val viewModel: FuneralInputItemViewModel) : BaseInputTextItem(viewModel) {

    override fun bind(
        binding: ItemInputTextBinding,
        position: Int
    ) {
        super.bind(binding, position)
        binding.btnPlus.setOnClickListener { viewModel.onClickPlusButton() }
        binding.btnMinus.setOnClickListener { viewModel.onClickMinusButton() }
    }

    override fun getId(): Long = viewModel.id
}
