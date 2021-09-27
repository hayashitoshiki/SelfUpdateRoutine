package com.myapp.data.local

import com.myapp.data.local.preferences.UserSharedPreferences
import com.myapp.domain.model.value.AlarmMode
import com.myapp.domain.repository.LocalSettingRepository
import java.time.LocalDateTime
import javax.inject.Inject

class LocalSettingRepositoryImp @Inject constructor(
    private val userSharedPreferences: UserSharedPreferences
) :
    LocalSettingRepository {

    // アラーム時間取得
    override fun getAlarmDate(): LocalDateTime {
        return userSharedPreferences.getAlarmDate()
    }

    // アラーム時間設定
    override fun saveAlarmDate(date: LocalDateTime) {
        userSharedPreferences.setAlarmDate(date)
    }

    // アラームモード取得
    override fun getAlarmMode(): AlarmMode {
        val modeValue = userSharedPreferences.getAlarmMode()
        return AlarmMode.fromValue(modeValue)
    }

    // アラームモード設定
    override fun saveAlarmMode(mode: AlarmMode) {
        userSharedPreferences.setAlarmMode(mode.value)
    }

    // 最終レポート時間取得
    override fun getLastReportSaveDateTime(): LocalDateTime {
        return userSharedPreferences.getLastReportSaveDateTime()
    }

    // 最終レポート時間更新
    override fun setLastReportSaveDateTime(date: LocalDateTime) {
        userSharedPreferences.setLastReportSaveDateTime(date)
    }
}
