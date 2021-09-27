package com.myapp.presentation.ui.home

import com.myapp.presentation.R
import com.myapp.presentation.databinding.ItemSingleTextBinding
import com.xwray.groupie.databinding.BindableItem
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * リサイクルビュー_レポート詳細項目表示用アイテム基盤
 *
 * @property reportDetail レポート項目詳細
 */
class BaseSingleTextItem(private val reportDetail: ReportDetail) : BindableItem<ItemSingleTextBinding>() {

    override fun getLayout(): Int = R.layout.item_single_text

    override fun bind(
        binding: ItemSingleTextBinding,
        position: Int
    ) {
        val date = reportDetail.date.date
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("M月d日(E)", Locale.JAPANESE)
        binding.txtDay.text = date.format(formatter)
        binding.txtValue.text = reportDetail.value
    }
}
