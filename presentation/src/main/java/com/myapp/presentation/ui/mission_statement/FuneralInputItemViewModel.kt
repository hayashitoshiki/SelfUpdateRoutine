package com.myapp.presentation.ui.mission_statement

import androidx.lifecycle.viewModelScope
import com.myapp.presentation.utils.BaseInputTextItemViewModel
import kotlinx.coroutines.launch

/**
 * 理想の葬式アイテム_画面ロジック
 *
 * @property index 理想の葬式リストのインデックス
 * @property value 理想の葬式の文字列
 */
class FuneralInputItemViewModel(
    private val index: Int,
    val id: Long,
    text: String
) : BaseInputTextItemViewModel() {
    private var first = true

    init {
        value.value = text
        _isMinusButtonEnable.value = index != 0
        value.observeForever {
            if (!first) {
                viewModelScope.launch {
                    MissionStatementDispatcher.changeFuneralText(index, it)
                }
            }
            first = false
        }
    }

    // 追加ボタン
    fun onClickPlusButton() = viewModelScope.launch {
        MissionStatementDispatcher.addFuneral(index + 1)
    }

    // 削除ボタン
    fun onClickMinusButton() = viewModelScope.launch {
        MissionStatementDispatcher.deleteFuneral(index)
    }

}