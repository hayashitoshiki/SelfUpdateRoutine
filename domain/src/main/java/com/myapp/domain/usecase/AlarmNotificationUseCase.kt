package com.myapp.domain.usecase

import java.time.LocalDateTime

/**
 * アラーム通知機能
 *
 */
interface AlarmNotificationUseCase {

    /**
     * 次回の通知時間を返す
     *
     * アラームモード = 高　かつ 設定した時間 < 現在の時間 < 23:59:59
     * であれば現在＋５秒後の時間を返す
     * 上記以外であれば設定した時間を返す
     *
     * @return 次回通知バーを出す時間
     */
    fun getNextAlarmDateTime(): LocalDateTime
}