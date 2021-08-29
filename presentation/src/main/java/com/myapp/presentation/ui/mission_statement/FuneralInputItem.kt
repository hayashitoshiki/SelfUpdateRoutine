package com.myapp.presentation.ui.mission_statement

import android.content.Context
import com.myapp.presentation.R
import com.myapp.presentation.databinding.ItemInputTextBinding
import com.myapp.presentation.utils.BaseInputTextItem

/**
 * ミッションステートメント_理想の葬儀リストアイテム
 *
 * @property viewModel 理想の葬儀リストアイテムの画面ロジック
 */
class FuneralInputItem(
    private val context: Context,
    private val viewModel: FuneralInputItemViewModel
) : BaseInputTextItem(viewModel) {

    override fun bind(
        binding: ItemInputTextBinding,
        position: Int
    ) {
        super.bind(binding, position)
        binding.edtInput.hint = context.getString(R.string.hint_funeral)
        binding.btnPlus.setOnClickListener { viewModel.onClickPlusButton() }
        binding.btnMinus.setOnClickListener { viewModel.onClickMinusButton() }
    }

    override fun getId(): Long = viewModel.id
}
