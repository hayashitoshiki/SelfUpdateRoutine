package com.myapp.presentation.ui.mission_statement

import com.myapp.presentation.databinding.ItemInputTextBinding
import com.myapp.presentation.utils.BaseInputTextItem

/**
 * ミッションステートメント_憲法リストアイテム
 *
 * @property viewModel 憲法リストアイテムの画面ロジック
 */
class ConstitutionInputItem(private val viewModel: ConstitutionInputItemViewModel) : BaseInputTextItem(viewModel) {

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
