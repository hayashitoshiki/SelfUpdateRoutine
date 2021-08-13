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

    /**
     * アラームモード取得
     *
     * アラームモードを設定していない場合０を返す
     *
     * @return アラーム時間
     */
    fun getAlarmMode(): Int

    /**
     * アラームモード設定
     *
     * @param mode アラームモード
     */
    fun setAlarmMode(mode: Int)

    /**
     * 最終レポート記録時間取得
     *
     * @return 最終レポート記録時間
     */
    fun getLastReportSaveDateTime(): LocalDateTime

    /**
     * 最終レポート記録時間設定
     *
     * @param date 最終レポート記録時間
     */
    fun setLastReportSaveDateTime(date: LocalDateTime)

}
