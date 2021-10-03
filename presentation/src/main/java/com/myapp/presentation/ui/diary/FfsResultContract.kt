package com.myapp.presentation.ui.diary

import com.myapp.presentation.utils.base.BaseContract

interface FfsResultContract {

    /**
     * 登録画面 状態保持
     *
     * @property fact 事象
     * @property find 発見
     * @property learn 教訓
     * @property statement 宣言
     */
    data class State(
        val fact: String = "",
        val find: String = "",
        val learn: String = "",
        val statement: String = ""
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
         * 次へボタン押下
         */
        object OnClickNextButton : Event()
    }
}