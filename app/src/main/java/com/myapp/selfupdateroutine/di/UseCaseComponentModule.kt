package com.myapp.selfupdateroutine.di

import com.myapp.domain.usecase.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseComponentModule {

    // UseCase
    @Binds
    abstract fun bindAlarmNotificationUseCase(
        alarmNotificationUseCaseImp: AlarmNotificationUseCaseImp
    ): AlarmNotificationUseCase

    @Binds
    abstract fun bindMissionStatementUseCase(missionStatementUseCaseImp: MissionStatementUseCaseImp): MissionStatementUseCase

    @Binds
    abstract fun bindReportUseCase(reportUseCaseImp: ReportUseCaseImp): ReportUseCase

    @Binds
    abstract fun bindSettingUseCaseImp(settingUseCaseImp: SettingUseCaseImp): SettingUseCase

    @Binds
    abstract fun bindAuthUseCaseImp(authUseCaseImp: AuthUseCaseImp): AuthUseCase
}
