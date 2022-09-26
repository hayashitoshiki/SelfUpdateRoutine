package com.myapp.presentation.ui.home

import com.myapp.presentation.R
import com.myapp.presentation.databinding.ItemListBinding
import com.xwray.groupie.databinding.BindableItem


class ListItem(private val text: String) : BindableItem<ItemListBinding>() {
    override fun getLayout(): Int = R.layout.item_list

    override fun bind(
        viewBinding: ItemListBinding,
        position: Int
    ) {
        viewBinding.text = text
    }
}