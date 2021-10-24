package com.myapp.domain.usecase

import com.myapp.domain.dto.AuthInputDto
import com.myapp.domain.dto.SignInDto
import com.myapp.domain.model.entity.Account
import com.myapp.domain.repository.LocalMissionStatementRepository
import com.myapp.domain.repository.LocalReportRepository
import com.myapp.domain.repository.RemoteAccountRepository
import com.myapp.domain.repository.RemoteReportRepository
import timber.log.Timber
import javax.inject.Inject

// 認証系機能
class AuthUseCaseImp @Inject constructor(
    private val remoteUserRepository: RemoteAccountRepository,
    private val remoteReportRepository: RemoteReportRepository,
    private val localReportRepository: LocalReportRepository,
    private val localMissionStatementRepository: LocalMissionStatementRepository
) : AuthUseCase {

    // 自動認証
    override fun autoAuth(): Boolean = remoteUserRepository.autoAuth()


    // アカウント情報取得
    override fun getAccountDetail() : Account? {
        return remoteUserRepository.getAccountDetail()
    }

    // ログイン
    override suspend fun signIn(signInDto: SignInDto) {
        remoteUserRepository.signIn(signInDto.email, signInDto.password)
        runCatching {
            remoteReportRepository.getAllReport(signInDto.email).forEach{
                localReportRepository.saveReport(it)
            }
        }.onFailure { Timber.e(it) }

    }

    // ログアウト
    override suspend fun signOut() {
        remoteUserRepository.signOut()
        runCatching {
            localReportRepository.deleteAll()
            localMissionStatementRepository.deleteAll()
        }.onFailure { Timber.e(it) }
    }

    // アカウント作成
    override suspend fun signUp(authInputDto: AuthInputDto) {
        remoteUserRepository.signUp(authInputDto.email, authInputDto.password)
        runCatching {
            val reportList = localReportRepository.getAllReport()
            remoteReportRepository.saveReport(reportList, authInputDto.email.value)
        }.onFailure { Timber.e(it) }
    }

    // アカウント削除
    override suspend fun delete() {
        remoteUserRepository.delete()
        runCatching {
            localReportRepository.deleteAll()
            localMissionStatementRepository.deleteAll()
        }.onFailure { Timber.e(it) }
    }
}