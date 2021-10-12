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
 * @property id 憲法の文字列のID
 * @property text 憲法の文字列
 */
class ConstitutionInputItemViewModel @AssistedInject constructor(
    @Assisted index: Int,
    @Assisted id: Long,
    @Assisted text: String
) : BaseInputTextItemViewModel(index, id, text) {

    // テキスト変更
    override fun changeText(text: String) {
        viewModelScope.launch {
            setState { copy(text = text) }
            MissionStatementDispatcher.setActions(MissionStatementDispatcherContract.Action.ChangeConstitutionText(id, text))
        }
    }

    // 追加ボタン
    override fun onClickPlusButton() {
        viewModelScope.launch {
            MissionStatementDispatcher.setActions(MissionStatementDispatcherContract.Action.AddConstitution(index + 1))
        }
    }

    // 削除ボタン
    override fun onClickMinusButton() {
        viewModelScope.launch {
            MissionStatementDispatcher.setActions(MissionStatementDispatcherContract.Action.DeleteConstitution(index))
        }
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
