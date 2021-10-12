package com.myapp.presentation.utils.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * リサイクルビュー_テキスト入力用アイテム 画面ベースロジック
 *
 */
abstract class BaseInputTextItemViewModel(val index: Int, val id: Long, text: String) :
    BaseAacViewModel<BaseInputTextItemContract.State, BaseInputTextItemContract.Effect, BaseInputTextItemContract.Event>() {

    private var first = true
    val value = MutableLiveData<String>()

    init {
        value.value = text
        setState { copy(text = text, isMinusButtonVisibility = index != 0) }
        value.observeForever {
            if (!first) {
                viewModelScope.launch {
                    setEvent(BaseInputTextItemContract.Event.ChangeText(it))
                }
            }
            first = false
        }
    }

    override fun initState(): BaseInputTextItemContract.State {
        return BaseInputTextItemContract.State()
    }

    override fun handleEvents(event: BaseInputTextItemContract.Event) = when(event) {
        is BaseInputTextItemContract.Event.ChangeText -> changeText(event.value)
        is BaseInputTextItemContract.Event.OnClickMinusButton -> onClickMinusButton()
        is BaseInputTextItemContract.Event.OnClickPlusButton -> onClickPlusButton()
    }

    protected abstract fun changeText(text: String)
    protected abstract fun onClickPlusButton()
    protected abstract fun onClickMinusButton()
}
