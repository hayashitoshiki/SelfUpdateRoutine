package com.myapp.domain.usecase

import com.myapp.domain.dto.AuthInputDto
import com.myapp.domain.dto.SignInDto
import com.myapp.domain.model.entity.Account

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
     * アカウント情報取得
     *
     * @return アカウント情報
     */
    fun getAccountDetail() : Account?

    /**
     * ログイン
     *
     * @param signInDto 認証用Input値
     */
    suspend fun signIn(signInDto: SignInDto)

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