package com.myapp.selfupdateroutine.preferences


import android.util.Log
import com.myapp.common.getDateTimeNow
import com.myapp.data.local.preferences.UserSharedPreferences
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset

class UserSharedPreferencesImp(private val preferenceManager: PreferenceManager) : UserSharedPreferences {

    // アラーム設定時間取得
    override fun getAlarmDate(): LocalDateTime {
        val milliseconds = preferenceManager.getLong(PreferenceKey.LongKey.ALARM_DATE)
        return if (milliseconds != 0L) {
            Log.d("TAG", "not null")
            LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneOffset.UTC)
        } else {
            val date = getDateTimeNow().with(LocalTime.of(22, 0))
            Log.d("TAG", "null")
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
}
