package com.myapp.presentation.ui.mission_statement

import android.content.Context
import androidx.core.content.ContextCompat
import com.myapp.presentation.databinding.ItemDiscBinding
import com.myapp.presentation.utils.base.DiscItem

/**
 * 「・」から始まる文字列リストアイテム(ミッションステートメント設定画面用)
 *
 * @property value 表示する文字列
 */
class MissionStatementSettingResultDiscItem(
    private val id: Long,
    private val value: String,
    private val textColor: Int,
    private val context: Context
) : DiscItem(value) {

    override fun bind(
        binding: ItemDiscBinding,
        position: Int
    ) {
        val color = ContextCompat.getColor(context, textColor)
        binding.txtValue.text = value
        binding.txtDisc.setTextColor(color)
        binding.txtValue.setTextColor(color)
    }

    override fun getId(): Long = id
}
