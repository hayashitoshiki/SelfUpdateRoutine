package com.myapp.domain.usecase

import com.myapp.domain.dto.AuthInputDto
import com.myapp.domain.dto.SignInDto
import com.myapp.domain.repository.RemoteAccountRepository
import javax.inject.Inject

// 認証系機能
class AuthUseCaseImp @Inject constructor(private val remoteUserRepository: RemoteAccountRepository) : AuthUseCase {

    // 自動認証
    override fun autoAuth(): Boolean = remoteUserRepository.autoAuth()

    // ログイン
    override suspend fun signIn(signInDto: SignInDto) {
        remoteUserRepository.signIn(signInDto.email, signInDto.password)
    }

    // ログアウト
    override suspend fun signOut() {
        remoteUserRepository.signOut()
    }

    // アカウント作成
    override suspend fun signUp(authInputDto: AuthInputDto) {
        remoteUserRepository.signUp(authInputDto.email, authInputDto.password)
    }

    // アカウント削除
    override suspend fun delete() {
        remoteUserRepository.delete()
    }
}