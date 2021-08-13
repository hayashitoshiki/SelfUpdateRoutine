package com.myapp.domain.usecase

import com.myapp.domain.model.value.AlarmMode
import com.myapp.domain.repository.LocalSettingRepository
import timber.log.Timber
import java.time.LocalDateTime
import java.time.LocalTime

// アラーム通知機能
class AlarmNotificationUseCaseImp(private val localSettingRepository: LocalSettingRepository) : AlarmNotificationUseCase {

    // 通知バーの時間を返す
    override fun getNextAlarmDateTime(): LocalDateTime {
        Timber.tag(this.javaClass.simpleName)
            .d("*******************")
        Timber.tag(this.javaClass.simpleName)
            .d("アラート時間設定")

        val lastSaveDateTime = localSettingRepository.getLastReportSaveDateTime()
        val lastSaveDate = lastSaveDateTime.toLocalDate()
        val nowDateTime = LocalDateTime.now()
        val nowDate = nowDateTime.toLocalDate()
        val nowTime = nowDateTime.toLocalTime()
        val maxTime = LocalTime.of(23, 59, 59)
        val minTime = localSettingRepository.getAlarmDate()
            .toLocalTime()
        val alarmMode = localSettingRepository.getAlarmMode()
        val nextAlertTime = if (alarmMode == AlarmMode.HARD && lastSaveDate.isBefore(nowDate) && nowTime.isAfter(
                minTime
            ) && nowTime.isBefore(maxTime)) {
            nowDateTime.plusSeconds(5)
        } else if (nowDateTime.hour == 0) {
            LocalDateTime.now()
                .with(LocalTime.of(minTime.hour, minTime.minute, minTime.second, minTime.nano))
        } else {
            val nextAlert = LocalDateTime.now()
                .with(LocalTime.of(minTime.hour, minTime.minute, minTime.second, minTime.nano))
            nextAlert.plusDays(1)
        }
        Timber.tag(this.javaClass.simpleName)
            .d("次回のアラート時間 = %s", nextAlertTime)
        Timber.tag(this.javaClass.simpleName)
            .d("*******************")
        return nextAlertTime
    }

    // 通知表示するか判定
    override fun checkAlarmNotificationEnable(): Boolean {
        val lastSaveDateTime = localSettingRepository.getLastReportSaveDateTime()
        val lastSaveDate = lastSaveDateTime.toLocalDate()
        val nowDateTime = LocalDateTime.now()
        val nowDate = nowDateTime.toLocalDate()
        return lastSaveDate != nowDate
    }
}