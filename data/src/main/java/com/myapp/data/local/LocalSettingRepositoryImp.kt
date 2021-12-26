package com.myapp.data.local

import com.myapp.common.getDateTimeNow
import com.myapp.data.local.preferences.PreferenceManager
import com.myapp.domain.model.value.AlarmMode
import com.myapp.domain.repository.LocalSettingRepository
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import javax.inject.Inject

class LocalSettingRepositoryImp @Inject constructor(
    private val preferenceManager: PreferenceManager
) :
    LocalSettingRepository {

    // アラーム時間取得
    override fun getAlarmDate(): LocalDateTime {
        val milliseconds = preferenceManager.getLong(PreferenceManager.Key.LongKey.ALARM_DATE)
        return if (milliseconds != 0L) {
            LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneOffset.UTC)
        } else {
            getDateTimeNow().with(LocalTime.of(22, 0))
        }
    }

    // アラーム時間設定
    override fun saveAlarmDate(date: LocalDateTime) {
        val value = date.atZone(ZoneOffset.UTC)
            .toInstant()
            .toEpochMilli()
        preferenceManager.setLong(PreferenceManager.Key.LongKey.ALARM_DATE, value)
    }

    // アラームモード取得
    override fun getAlarmMode(): AlarmMode {
        val modeValue =  preferenceManager.getInt(PreferenceManager.Key.IntKey.ALARM_MODE)
        return AlarmMode.fromValue(modeValue)
    }

    // アラームモード設定
    override fun saveAlarmMode(mode: AlarmMode) {
        preferenceManager.setInt(PreferenceManager.Key.IntKey.ALARM_MODE, mode.value)
    }

    // 最終レポート時間取得
    override fun getLastReportSaveDateTime(): LocalDateTime {
        val milliseconds = preferenceManager.getLong(PreferenceManager.Key.LongKey.LAST_SAVE_DATE)
        return if (milliseconds != 0L) {
            LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneOffset.UTC)
        } else {
            LocalDateTime.of(2000, 1, 1, 0, 0, 0)
        }
    }

    // 最終レポート時間更新
    override fun setLastReportSaveDateTime(date: LocalDateTime) {
        val value = date.atZone(ZoneOffset.UTC)
            .toInstant()
            .toEpochMilli()
        preferenceManager.setLong(PreferenceManager.Key.LongKey.LAST_SAVE_DATE, value)
    }
}
