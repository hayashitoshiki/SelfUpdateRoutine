package com.myapp.presentation.ui.mission_statement

import androidx.lifecycle.viewModelScope
import com.myapp.presentation.utils.BaseInputTextItemViewModel
import kotlinx.coroutines.launch

/**
 * 憲法アイテム_画面ロジック
 *
 * @property index 憲法リストのインデックス
 * @property value 憲法の文字列
 */
class ConstitutionInputItemViewModel(
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
                    MissionStatementDispatcher.changeConstitutionText(index, it)
                }
            }
            first = false
        }
    }

    // 追加ボタン
    fun onClickPlusButton() = viewModelScope.launch {
        MissionStatementDispatcher.addConstitution(index + 1)
    }

    // 削除ボタン
    fun onClickMinusButton() = viewModelScope.launch {
        MissionStatementDispatcher.deleteConstitution(index)
    }

}