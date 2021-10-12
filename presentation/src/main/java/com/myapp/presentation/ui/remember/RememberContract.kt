package com.myapp.presentation.ui.remember

import com.myapp.presentation.R
import com.myapp.presentation.utils.base.BaseContract
import com.myapp.presentation.utils.base.StringResource

interface RememberContract {
    /**
     * 　設定画面　状態保持
     *
     * @property factComment 事実
     * @property findComment 発見
     * @property learnComment 学び
     * @property statementComment 宣言
     * @property heartScoreComment 点数
     * @property reasonComment 理数
     * @property improveComment 改善点
     * @property date 日付
     * @property heartScoreImg 画像イメージ
     */
    data class State(
        val factComment: String = "",
        val findComment: String = "",
        val learnComment: String = "",
        val statementComment: String = "",
        val heartScoreComment: StringResource = StringResource.from(R.string.weather_cloudy),
        val reasonComment: String = "",
        val improveComment: String = "",
        val date: String = "",
        val heartScoreImg: Int = 0,
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
    sealed class Event : BaseContract.Event
}

