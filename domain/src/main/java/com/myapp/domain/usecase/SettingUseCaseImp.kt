package com.myapp.domain.usecase

import com.myapp.domain.dto.NextAlarmTimeInputDto
import com.myapp.domain.model.value.AlarmMode
import com.myapp.domain.repository.LocalSettingRepository
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

// 設定機能関連
class SettingUseCaseImp @Inject constructor(
    private val localSettingRepository: LocalSettingRepository
) : SettingUseCase {

    // アラーム時間取得
    override fun getAlarmDate(): LocalDateTime {
        return localSettingRepository.getAlarmDate()
    }

    // アラームモード取得
    override fun getAlarmMode(): AlarmMode {
        return localSettingRepository.getAlarmMode()
    }

    // アラーム設定更新
    override fun updateAlarmDate(
        dto: NextAlarmTimeInputDto
    ): LocalDateTime {
        val nextAlertTime = getNextAlarmDate(dto)
        localSettingRepository.saveAlarmDate(nextAlertTime)
        localSettingRepository.saveAlarmMode(dto.mode)
        return nextAlertTime
    }

    // 次回のアラーム時間取得
    override fun getNextAlarmDate(dto: NextAlarmTimeInputDto): LocalDateTime {
        val lastSaveDateTime = localSettingRepository.getLastReportSaveDateTime()
        val lastSaveDate = lastSaveDateTime.toLocalDate()
        val nowDateTime = LocalDateTime.now()
        val nowDate = nowDateTime.toLocalDate()
        var nextAlertTime = nowDateTime.with(LocalTime.of(dto.hour, dto.minute, dto.second))
        if (lastSaveDate.isEqual(nowDate) || (!lastSaveDate.isEqual(nowDate) && nextAlertTime.isBefore(nowDateTime))) {
            nextAlertTime = nextAlertTime.plusDays(1)
        }
        return nextAlertTime
    }
}
