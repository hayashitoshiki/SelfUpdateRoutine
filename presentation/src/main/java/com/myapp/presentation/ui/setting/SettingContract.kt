package com.myapp.presentation.ui.setting

import com.myapp.domain.model.value.AlarmMode
import com.myapp.presentation.R
import com.myapp.presentation.utils.base.BaseContract
import com.myapp.presentation.utils.expansion.explanation
import java.time.LocalDateTime

interface SettingContract {
    /**
     * 　設定画面　状態保持
     *
     * @property beforeDate 変更前のアラーム設定時間
     * @property hourDate アラーム時間
     * @property minutesDate アラーム分
     * @property secondsDate アラーム秒
     * @property alarmTimeDiff アラーム時間の変化
     * @property alarmTimeDiffColor アラーム時間の変化の文字色
     * @property nextAlarmTime 次回のアラーム時間
     * @property beforeAlarmMode 変更前のアラームモード
     * @property alarmMode 変更後のアラームモード
     * @property alarmModeDiffColor アラームモードの変化の文字色
     * @property alarmModeExplanation 選択中のアラームモードの説明文
     * @property isEnableConfirmButton 変更ボタンの活性・非活性制御
     */
    data class State(
        val beforeDate: String = "22:00",
        val hourDate: Int = 22,
        val minutesDate: Int = 0,
        val secondsDate: Int = 0,
        val alarmTimeDiff: String = "22:00 -> 22:00",
        val alarmTimeDiffColor: Int = R.color.text_color_light_secondary,
        val nextAlarmTime: String = "1月1日(日) 22:00",
        var beforeAlarmMode: AlarmMode = AlarmMode.NORMAL,
        val alarmMode: AlarmMode = AlarmMode.NORMAL,
        val alarmModeDiffColor: Int = R.color.text_color_light_secondary,
        val alarmModeExplanation: Int = AlarmMode.NORMAL.explanation,
        val isEnableConfirmButton: Boolean = false
    ) : BaseContract.State

    /**
     * UIイベント
     *
     */
    sealed class Effect : BaseContract.Effect {
        /**
         * 次の画面へ遷移
         */
        data class NextNavigation(val value: LocalDateTime) : Effect()

        /**
         * 次の画面へ遷移
         */
        data class ShowError(val throwable: Throwable) : Effect()
    }

    /**
     * アクション
     *
     */
    sealed class Event : BaseContract.Event {
        /**
         * 画面表示
         */
        object CreatedView : Event()

        /**
         * アラーム分変更
         *
         * @property value 入力したテキスト
         */
        data class OnChangeAlarmMinute(val value: Int) : Event()

        /**
         * アラーム時間変更
         *
         * @property value 入力したテキスト
         */
        data class OnChangeAlarmHour(val value: Int) : Event()

        /**
         * アラートモード変更
         *
         * @property value 入力したテキスト
         */
        data class OnChangeAlarmMode(val value: AlarmMode) : Event()

        /**
         * 次へボタン押下
         */
        object OnClickNextButton : Event()
    }
}

