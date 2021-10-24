package com.myapp.domain.usecase

import com.myapp.domain.dto.AuthInputDto
import com.myapp.domain.dto.SignInDto
import com.myapp.domain.model.entity.Account
import com.myapp.domain.repository.LocalReportRepository
import com.myapp.domain.repository.RemoteAccountRepository
import com.myapp.domain.repository.RemoteReportRepository
import javax.inject.Inject

// 認証系機能
class AuthUseCaseImp @Inject constructor(
    private val remoteUserRepository: RemoteAccountRepository,
    private val remoteReportRepository: RemoteReportRepository,
    private val localReportRepository: LocalReportRepository
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
        remoteReportRepository.getAllReport(signInDto.email).forEach{
            localReportRepository.saveReport(it)
        }
    }

    // ログアウト
    override suspend fun signOut() {
        remoteUserRepository.signOut()
        localReportRepository.deleteAll()
    }

    // アカウント作成
    override suspend fun signUp(authInputDto: AuthInputDto) {
        remoteUserRepository.signUp(authInputDto.email, authInputDto.password)
        localReportRepository.getAllReport().forEach{
            remoteReportRepository.saveReport(it, authInputDto.email.value)
        }
    }

    // アカウント削除
    override suspend fun delete() {
        remoteUserRepository.delete()
        localReportRepository.deleteAll()
    }
}