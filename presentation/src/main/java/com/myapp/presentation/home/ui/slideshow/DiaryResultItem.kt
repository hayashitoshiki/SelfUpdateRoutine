package com.myapp.presentation.home.ui.slideshow

import androidx.lifecycle.LifecycleOwner
import com.myapp.presentation.R
import com.myapp.presentation.databinding.ItemDiaryResultBinding
import com.xwray.groupie.OnItemClickListener
import com.xwray.groupie.databinding.BindableItem
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * 振り返り_最終確認画面
 */
class DiaryResultItem(
    private val listener: OnItemClickListener,
    val viewLifecycleOwner: LifecycleOwner,
    private val viewModel: SlideshowViewModel
) : BindableItem<ItemDiaryResultBinding>() {

    override fun getLayout(): Int = R.layout.item_diary_result

    @ExperimentalCoroutinesApi
    override fun bind(
        binding: ItemDiaryResultBinding,
        position: Int
    ) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        // 確定ボタン
        binding.btnOk.setOnClickListener { // TODO : 登録完了したら遷移するよう後で変更
            viewModel.saveReport()
            listener.onItemClick(getItem(itemCount - 1), it)
        }

    }
}
