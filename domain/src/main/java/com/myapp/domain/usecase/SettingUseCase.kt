package com.myapp.domain.usecase

import java.time.LocalDateTime

/**
 * 設定関連機能
 */
interface SettingUseCase {

    /**
     * 現在のアラーム設定時間取得
     */
    fun getAlarmDate(): LocalDateTime

    /**
     * アラーム設定時間更新
     */
    fun updateAlarmDate(date: LocalDateTime)
}