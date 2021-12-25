package com.myapp.presentation.ui.diary

import com.myapp.presentation.utils.base.BaseContract
import com.myapp.presentation.utils.base.Status

interface WeatherResultContract {

    /**
     * 登録画面 状態保持
     *
     * @property fact 事象
     * @property find 発見
     * @property learn 教訓
     * @property statement 宣言
     * @property assessment  点数
     * @property reason　理由
     * @property improve 目標
     */
    data class State(
        val fact: String = "",
        val find: String = "",
        val learn: String = "",
        val statement: String = "",
        val assessment: Int = 0,
        val reason: String = "",
        val improve: String = ""
    ) : BaseContract.State

    /**
     * UIイベント
     *
     */
    sealed class Effect : BaseContract.Effect {
        /**
         * 保存ボタンタップ後アクション
         *
         * @property status 成功判定ステータス
         */
        data class SaveResult(val status: Status<*>) : Effect()
    }

    /**
     * アクション
     *
     */
    sealed class Event : BaseContract.Event {
        /**
         * 保存ボタン押下
         */
        object OnClickSaveButton : Event()
    }
}