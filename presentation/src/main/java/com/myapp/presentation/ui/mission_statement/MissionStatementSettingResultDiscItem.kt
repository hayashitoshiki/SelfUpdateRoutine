package com.myapp.presentation.ui.mission_statement

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
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
    private val textColor: LiveData<Int>,
    private val context: Context,
    private val viewLifecycleOwner: LifecycleOwner
) : DiscItem(value) {

    override fun bind(
        binding: ItemDiscBinding,
        position: Int
    ) {
        binding.txtValue.text = value
        textColor.value?.let { setTextColor(it, binding) }
        textColor.observe(viewLifecycleOwner, { setTextColor(it, binding) })
    }

    // 文字色設定
    private fun setTextColor(
        resId: Int,
        binding: ItemDiscBinding
    ) {
        val color = ContextCompat.getColor(context, resId)
        binding.txtDisc.setTextColor(color)
        binding.txtValue.setTextColor(color)
    }

    override fun getId(): Long = id
}
