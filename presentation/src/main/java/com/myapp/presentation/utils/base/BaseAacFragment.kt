package com.myapp.presentation.utils.base

import android.os.Bundle
import android.view.View

/**
 * AAC×MVI用BaseFragment
 *
 * @param UiState Viewの状態
 * @param Effect UIイベント
 * @param Event ユーザーアクション
 */
abstract class BaseAacFragment<UiState : BaseContract.State, Effect : BaseContract.Effect, Event : BaseContract.Event>
    : BaseFragment() {

    protected abstract val viewModel : BaseAacViewModel<UiState, Effect, Event>

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        setEvent()
        viewModel.effect.observe(viewLifecycleOwner, { setEffect(it) })
        viewModel.state.observe(viewLifecycleOwner, { changedState(it) })
    }

    /**
     * Event通知設定
     *
     */
    protected abstract fun setEvent()

    /**
     * Effect処理設定
     *
     * @param effect UIイベント
     */
    protected abstract fun setEffect(effect: Effect)

    /**
     * State反映設定
     *
     * @param state Viewの状態
     */
    protected abstract fun changedState(state: UiState)
}

