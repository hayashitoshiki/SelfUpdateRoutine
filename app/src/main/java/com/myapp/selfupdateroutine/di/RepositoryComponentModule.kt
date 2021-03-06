package com.myapp.selfupdateroutine.di

import com.myapp.data.local.LocalMissionStatementRepositoryImp
import com.myapp.data.local.LocalReportRepositoryImp
import com.myapp.data.local.LocalSettingRepositoryImp
import com.myapp.data.remote.RemoteAccountRepositoryImp
import com.myapp.data.remote.RemoteReportRepositoryImp
import com.myapp.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Repository 依存性注入
 *
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryComponentModule {

    @Binds
    abstract fun bindLocalMissionStatementRepository(
        localMissionStatementRepositoryImp: LocalMissionStatementRepositoryImp
    ): LocalMissionStatementRepository

    @Binds
    abstract fun bindLocalReportRepository(localReportRepositoryImp: LocalReportRepositoryImp): LocalReportRepository

    @Binds
    abstract fun bindLocalSettingRepository(localSettingRepositoryImp: LocalSettingRepositoryImp): LocalSettingRepository

    @Binds
    abstract fun bindRemoteAccountRepository(remoteAccountRepositoryImp: RemoteAccountRepositoryImp): RemoteAccountRepository

    @Binds
    abstract fun bindRemoteReportRepository(remoteReportRepository: RemoteReportRepositoryImp): RemoteReportRepository
}
