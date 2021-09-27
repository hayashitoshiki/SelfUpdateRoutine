package com.myapp.presentation.ui.mission_statement

import android.content.Context
import android.graphics.Typeface
import android.util.TypedValue
import com.myapp.presentation.R
import com.myapp.presentation.databinding.ItemDiscBinding
import com.myapp.presentation.utils.DiscItem

/**
 * 「・」から始まる文字列リストアイテム(ミッションステートメント一覧画面用)
 *
 * @property value 表示する文字列
 */
open class MissionStatementListDiscItem(
    private val value: String,
    private val context: Context
) : DiscItem(value) {

    override fun getLayout(): Int = R.layout.item_disc

    override fun bind(
        binding: ItemDiscBinding,
        position: Int
    ) {
        binding.txtValue.also {
            it.text = value
            it.setTypeface(binding.txtDisc.typeface, Typeface.BOLD_ITALIC)
            it.setTextColor(context.resources.getColor(R.color.text_color_dark_primary, null))
            it.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.resources.getDimension(R.dimen.title_text))
        }
        binding.txtDisc.also {
            it.setTypeface(binding.txtDisc.typeface, Typeface.BOLD_ITALIC)
            it.setTextColor(context.resources.getColor(R.color.text_color_dark_primary, null))
            it.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.resources.getDimension(R.dimen.title_text))
        }
    }
}
