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
 * 理想の葬式アイテム_画面ロジック
 *
 * @property index 理想の葬式リストのインデックス
 * @property id 理想の葬式の文字列のID
 * @property text 理想の葬式の文字列
 */
class FuneralInputItemViewModel @AssistedInject constructor(
    @Assisted index: Int,
    @Assisted val id: Long,
    @Assisted text: String
) : BaseInputTextItemViewModel(index, text) {

    // テキスト変更
    override fun changeText(text: String) {
        viewModelScope.launch {
            setState { copy(value = text) }
            MissionStatementDispatcher.setActions(MissionStatementDispatcherContract.Action.ChangeFuneralText(index, text))
        }
    }

    // 追加ボタン
    override fun onClickPlusButton() {
        viewModelScope.launch {
            MissionStatementDispatcher.setActions(MissionStatementDispatcherContract.Action.AddFuneral(index + 1))
        }
    }

    // 削除ボタン
    override fun onClickMinusButton() {
        viewModelScope.launch {
            MissionStatementDispatcher.setActions(MissionStatementDispatcherContract.Action.DeleteFuneral(index))
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            index: Int,
            id: Long,
            text: String
        ): FuneralInputItemViewModel
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
