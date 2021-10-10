package com.myapp.presentation.utils.base

interface BaseInputTextItemContract {

    /**
     * リサイクルビュー_テキスト入力用アイテム 状態保持
     *
     * @property value テキスト
     * @property isPlusButtonVisibility ＋ボタン表示制御
     * @property isMinusButtonVisibility ーボタン表示制御
     */
    data class State(
        val value: String = "",
        val isPlusButtonVisibility: Boolean = true,
        val isMinusButtonVisibility: Boolean = true
    ) : BaseContract.State

    /**
     * UIイベント
     *
     */
    sealed class Effect : BaseContract.Effect

    /**
     * アクション
     *
     */
    sealed class Event : BaseContract.Event {
        /**
         * ＋ボタンタップ
         */
        object OnClickPlusButton : Event()

        /**
         * ーボタンタップ
         */
        object OnClickMinusButton : Event()
        /**
         * テキスト変更
         */
        data class ChangeText(val value: String) : Event()
    }
}
