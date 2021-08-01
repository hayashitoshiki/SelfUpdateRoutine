package com.myapp.data.local

import com.myapp.data.local.preferences.UserSharedPreferences
import com.myapp.domain.repository.LocalSettingRepository
import java.time.LocalDateTime

class LocalSettingRepositoryImp(private val userSharedPreferences: UserSharedPreferences) : LocalSettingRepository {
    override fun getAlarmDate(): LocalDateTime {
        return userSharedPreferences.getAlarmDate()
    }

    override fun saveAlarmDate(date: LocalDateTime) {
        userSharedPreferences.setAlarmDate(date)
    }
}