package com.myapp.domain.usecase

import com.myapp.domain.model.value.AlarmMode
import com.myapp.domain.repository.LocalSettingRepository
import timber.log.Timber
import java.time.LocalDateTime
import java.time.LocalTime

class AlarmNotificationUseCaseImp(private val localSettingRepository: LocalSettingRepository) : AlarmNotificationUseCase {

    // 通知バーの時間を返す
    override fun getNextAlarmDateTime(): LocalDateTime {
        Timber.tag("AlarmNotificationUseCaseImp")
            .d("*******************")
        Timber.tag("AlarmNotificationUseCaseImp")
            .d("アラート時間設定")
        localSettingRepository.setLastReportSaveDateTime(LocalDateTime.now())

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
        } else {
            val nextAlert = LocalDateTime.now()
                .with(LocalTime.of(minTime.hour, minTime.minute, minTime.second, minTime.nano))
            nextAlert.plusDays(1)
        }
        Timber.tag("AlarmNotificationUseCaseImp")
            .d("次回のアラート時間 = %s", nextAlertTime)
        Timber.tag("AlarmNotificationUseCaseImp")
            .d("*******************")
        return nextAlertTime
    }
}