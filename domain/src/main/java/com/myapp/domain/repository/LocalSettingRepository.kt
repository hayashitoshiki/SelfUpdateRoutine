package com.myapp.domain.repository

import java.time.LocalDateTime

/**
 * ローカルデータへの設定機能のCRUD処理
 */
interface LocalSettingRepository {

    /**
     * 設定しているアラーム日付取得
     */
    fun getAlarmDate(): LocalDateTime

    /**
     *  アラーム日付更新
     */
    fun saveAlarmDate(date: LocalDateTime)
}