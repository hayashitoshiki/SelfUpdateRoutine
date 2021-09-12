package com.myapp.data.local.preferences


import com.myapp.common.getDateTimeNow
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset

class UserSharedPreferencesImp(private val preferenceManager: PreferenceManager) : UserSharedPreferences {

    // アラーム設定時間取得
    override fun getAlarmDate(): LocalDateTime {
        val milliseconds = preferenceManager.getLong(PreferenceKey.LongKey.ALARM_DATE)
        return if (milliseconds != 0L) {
            LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneOffset.UTC)
        } else {
            val date = getDateTimeNow().with(LocalTime.of(22, 0))
            date
        }
    }

    // アラーム設定時間更新
    override fun setAlarmDate(date: LocalDateTime) {
        val value = date.atZone(ZoneOffset.UTC)
            .toInstant()
            .toEpochMilli()
        preferenceManager.setLong(PreferenceKey.LongKey.ALARM_DATE, value)
    }

    // アラームモード取得
    override fun getAlarmMode(): Int {
        return preferenceManager.getInt(PreferenceKey.IntKey.ALARM_MODE)
    }

    // アラームモード更新
    override fun setAlarmMode(mode: Int) {
        return preferenceManager.setInt(PreferenceKey.IntKey.ALARM_MODE, mode)
    }

    // 最終レポート記録時間取得
    override fun getLastReportSaveDateTime(): LocalDateTime {
        val milliseconds = preferenceManager.getLong(PreferenceKey.LongKey.LAST_SAVE_DATE)
        return if (milliseconds != 0L) {
            LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneOffset.UTC)
        } else {
            val date = LocalDateTime.of(2000, 1, 1, 0, 0, 0)
            date
        }
    }

    // 最終レポート記録時間更新
    override fun setLastReportSaveDateTime(date: LocalDateTime) {
        val value = date.atZone(ZoneOffset.UTC)
            .toInstant()
            .toEpochMilli()
        preferenceManager.setLong(PreferenceKey.LongKey.LAST_SAVE_DATE, value)
    }
}
