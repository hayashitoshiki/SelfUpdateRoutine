package com.myapp.data.local.preferences

import java.time.LocalDateTime


/**
 * Preferenceへの設定情報格納
 */
interface UserSharedPreferences {

    /**
     * 時間取得
     *
     * @return アラーム時間
     */
    fun getAlarmDate(): LocalDateTime

    /**
     * 時間設定
     *
     * @param date アラーム時間
     */
    fun setAlarmDate(date: LocalDateTime)

}
