package com.myapp.domain.repository

import com.myapp.domain.model.value.Email
import com.myapp.domain.model.value.Password

/**
 * 外部に対するアカウント関連処理
 *
 */
interface RemoteAccountRepository {

    /**
     * 自動認証チェック
     *
     */
    fun autoAuth(): Boolean

    /**
     * ログイン
     *
     * @param email メールアドレス
     * @param password パスワード
     */
    suspend fun signIn(email: String, password: String)

    /**
     * 新規登録
     *
     * @param email メールアドレス
     * @param password パスワード
     */
    suspend fun signUp(email: Email, password: Password)

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