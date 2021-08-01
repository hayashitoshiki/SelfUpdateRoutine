package com.myapp.domain.usecase

import com.myapp.domain.repository.LocalSettingRepository
import java.time.LocalDateTime

class SettingUseCaseImp(private val localSettingRepository: LocalSettingRepository) : SettingUseCase {
    override fun getAlarmDate(): LocalDateTime {
        return localSettingRepository.getAlarmDate()
    }

    override fun updateAlarmDate(
        date: LocalDateTime
    ) {
        localSettingRepository.saveAlarmDate(date)
    }
}