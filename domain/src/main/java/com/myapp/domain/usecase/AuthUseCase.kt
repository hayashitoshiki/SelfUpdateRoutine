package com.myapp.domain.usecase

import com.myapp.domain.dto.AuthInputDto

/**
 * 認証系機能
 *
 */
interface AuthUseCase {

    /**
     * 自動認証
     *
     * @return 自動ログイン判定結果
     */
    fun autoAuth(): Boolean

    /**
     * ログイン
     *
     * @param authInputDto 認証用Input値
     */
    suspend fun signIn(authInputDto: AuthInputDto)

    /**
     * 新規登録
     *
     * @param authInputDto 認証用Input値
     */
    suspend fun signUp(authInputDto: AuthInputDto)

    /**
     * ログアウト
     *
     */
    suspend fun signOut()

    /**
     * アカウント削除
     *
     */
    suspend fun delete()
}