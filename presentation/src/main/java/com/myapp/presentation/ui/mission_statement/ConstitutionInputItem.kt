package com.myapp.presentation.ui.mission_statement

import android.content.Context
import com.myapp.presentation.R
import com.myapp.presentation.databinding.ItemInputTextBinding
import com.myapp.presentation.utils.base.BaseInputTextItem

/**
 * ミッションステートメント_憲法リストアイテム
 *
 * @property viewModel 憲法リストアイテムの画面ロジック
 */
class ConstitutionInputItem(
    private val context: Context,
    private val viewModel: ConstitutionInputItemViewModel
) : BaseInputTextItem(viewModel) {

    override fun bind(binding: ItemInputTextBinding, position: Int) {
        super.bind(binding, position)
        binding.edtInput.hint = context.getString(R.string.hint_constitution)
    }

    override fun getId(): Long = viewModel.id
}
