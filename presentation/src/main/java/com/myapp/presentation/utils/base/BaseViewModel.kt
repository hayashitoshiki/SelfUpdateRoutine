package com.myapp.presentation.utils.base

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

/**
 * UIロジック Base
 *
 *
 * ViewModel側の実装内容
 * ・initState・・・・State初期化
 * ・handleEvents・・アクションに対する処理定義
 *
 * Screen側の実装内容
 * ・setEvent()：アクション発行
 * ・effect.onEach {}：エフェクト受け取り
 *
 */
abstract class BaseViewModel<UiState : BaseContract.State, Effect : BaseContract.Effect, Event : BaseContract.Event> :
    ViewModel() {

    private val initialState: UiState by lazy { initState() }

    // 状態管理
    private val _state: MutableState<UiState> = mutableStateOf(initialState)
    val state: State<UiState> = _state

    // イベント処理
    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()

    // エフェクト
    private val _effect: Channel<Effect> = Channel()
    val effect: Flow<Effect> = _effect.receiveAsFlow()

    init {
        subscribeToEvents()
    }

    /**
     * イベントFlow関連付け
     *
     */
    private fun subscribeToEvents() {
        viewModelScope.launch {
            _event.collect {
                handleEvents(it)
            }
        }
    }

    /**
     * アクション発火
     *
     * @param event アクション
     */
    fun setEvent(event: Event) {
        viewModelScope.launch { _event.emit(event) }
    }

    /**
     * エフェクト設定
     *
     * @param builder
     */
    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }

    /**
     * 状態設定
     *
     * @param reducer
     */
    protected fun setState(reducer: UiState.() -> UiState) {
        val newState = state.value.reducer()
        _state.value = newState
    }

    /**
     * State初期化
     *
     * @return 初期化したState
     */
    abstract fun initState(): UiState

    /**
     * 各アクションに対する処理分岐
     *
     * @param event アクション
     */
    abstract fun handleEvents(event: Event)
}