package com.myapp.presentation.utils.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * UIロジック AAC用BaseViewModel
 *
 * cashStateとStateを比較して差分の生じた部分のみUI更新
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
abstract class BaseAacViewModel<UiState : BaseContract.State, Effect : BaseContract.Effect, Event : BaseContract.Event> :
    ViewModel() {
    private val initialState: UiState by lazy { initState() }

    // 状態管理
    private val _state: MutableLiveData<UiState> = MutableLiveData(initialState)
    val state: LiveData<UiState> = _state

    // 状態管理のキャッシュ
    var cashState: UiState? = null
    protected set

    // イベント処理
    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()

    // エフェクト
    private val _effect: MutableLiveData<Effect> = MutableLiveData()
    val effect: LiveData<Effect> = _effect

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
        viewModelScope.launch { _effect.value = effectValue }
    }

    /**
     * 状態設定
     *
     * @param reducer
     */
    protected fun setState(reducer: UiState.() -> UiState) {
        cashState = state.value
        val newState = state.value?.reducer() ?: return
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