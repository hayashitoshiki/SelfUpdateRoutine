package com.myapp.presentation.utils.base

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import com.myapp.presentation.R
import com.myapp.presentation.databinding.ItemInputTextBinding
import com.xwray.groupie.databinding.BindableItem

/**
 * リサイクルビュー_テキスト入力用アイテム
 *
 * @property viewModel 画面ロジック
 */
abstract class BaseInputTextItem(private val viewModel: BaseInputTextItemViewModel) : BindableItem<ItemInputTextBinding>() {

    override fun getLayout(): Int = R.layout.item_input_text

    override fun bind(
        binding: ItemInputTextBinding,
        position: Int
    ) {
        binding.viewModel = viewModel
        binding.edtInput.setText(viewModel.text, TextView.BufferType.NORMAL)
        binding.edtInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.setEvent(BaseInputTextItemContract.Event.ChangeText(s.toString()))
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        binding.btnPlus.setOnClickListener { viewModel.setEvent(BaseInputTextItemContract.Event.OnClickPlusButton) }
        binding.btnMinus.setOnClickListener { viewModel.setEvent(BaseInputTextItemContract.Event.OnClickMinusButton) }
    }
}
