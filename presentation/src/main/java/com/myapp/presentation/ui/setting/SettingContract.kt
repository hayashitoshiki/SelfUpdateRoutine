package com.myapp.presentation.ui.setting

import com.myapp.domain.model.value.AlarmMode
import com.myapp.presentation.utils.base.BaseContract
import java.time.LocalDateTime
import java.time.LocalTime

interface SettingContract {
    /**
     * 　設定画面　状態保持
     *
     * @property beforeTime 変更前のアラーム設定時間
     * @property afterTime 変更後のアラーム設定時間
     * @property beforeAlarmMode 変更前のアラームモード
     * @property afterAlarmMode 変更後のアラームモード
     * @property nextAlarmTime 次回のアラーム時間
     * @property isEnableConfirmButton 変更ボタンの活性・非活性制御
     */
    data class State(
        val beforeTime: LocalDateTime = LocalDateTime.now(),
        val afterTime: LocalTime = LocalTime.now(),
        val beforeAlarmMode: AlarmMode = AlarmMode.NORMAL,
        val afterAlarmMode: AlarmMode = AlarmMode.NORMAL,
        val nextAlarmTime: String = "1月1日(日) 22:00",
        val isEnableConfirmButton: Boolean = false,
        val init: Boolean = false
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

