package com.myapp.presentation.ui.mission_statement

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.myapp.presentation.utils.base.BaseInputTextItemViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

/**
 * 憲法アイテム_画面ロジック
 *
 * @property index 憲法リストのインデックス
 * @property value 憲法の文字列
 */
class ConstitutionInputItemViewModel @AssistedInject constructor(
    @Assisted private val index: Int,
    @Assisted val id: Long,
    @Assisted text: String
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

    @AssistedFactory
    interface Factory {
        fun create(
            index: Int,
            id: Long,
            text: String
        ): ConstitutionInputItemViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun provideFactory(
            factory: Factory,
            index: Int,
            id: Long,
            text: String
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return factory.create(index, id, text) as T
            }
        }
    }
}
