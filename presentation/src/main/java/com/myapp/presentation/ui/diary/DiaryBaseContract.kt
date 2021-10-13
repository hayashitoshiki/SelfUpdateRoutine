package com.myapp.presentation.ui.diary

import com.myapp.presentation.utils.base.BaseContract

interface DiaryBaseContract {

    /**
     * 登録画面 状態保持
     *
     * @property inputText 入力内容
     * @property hintText ヒント文言
     * @property hintVisibility ヒント文言表示/非表示
     * @property isButtonEnable 次へボタン活性/非活性
     */
    data class State(
        val inputText: String = "",
        val hintText: String = "",
        val hintVisibility: Boolean = false,
        val isButtonEnable: Boolean = false
    ) : BaseContract.State

    /**
     * UIイベント
     *
     */
    sealed class Effect : BaseContract.Effect {
        /**
         * 次の画面へ遷移
         */
        object NextNavigation : Effect()
    }

    /**
     * アクション
     *
     */
    sealed class Event : BaseContract.Event {

        /**
         * テキスト変更
         *
         * @property value 入力したテキスト
         */
        data class OnChangeText(val value: String) : Event()

        /**
         * 次へボタン押下
         */
        object OnClickNextButton : Event()
    }
}