package com.myapp.presentation.utils

import com.myapp.presentation.R
import com.myapp.presentation.databinding.ItemDiscBinding
import com.xwray.groupie.databinding.BindableItem

/**
 * 「・」から始まる文字列リストアイテム
 *
 * @property value 表示する文字列
 */
open class DiscItem(private val value: String) : BindableItem<ItemDiscBinding>() {

    override fun getLayout(): Int = R.layout.item_disc

    override fun bind(
        binding: ItemDiscBinding,
        position: Int
    ) {
        binding.txtValue.text = value
    }
}