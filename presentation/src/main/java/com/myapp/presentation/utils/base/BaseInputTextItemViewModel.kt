package com.myapp.presentation.utils.base

/**
 * リサイクルビュー_テキスト入力用アイテム 画面ベースロジック
 *
 */
abstract class BaseInputTextItemViewModel(val index: Int, val text: String) :
    BaseAacViewModel<BaseInputTextItemContract.State, BaseInputTextItemContract.Effect, BaseInputTextItemContract.Event>() {

    init {
        setState { copy(value = text, isMinusButtonVisibility = index != 0) }
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
