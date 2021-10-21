package com.myapp.data.remote.api

import com.myapp.domain.model.value.Email
import com.myapp.domain.model.value.Password

/**
 * Firebaseアクセス定義
 *
 */
interface FireBaseService {

    // region 認証系

    /**
     * ログイン状態チェック
     *
     */
    fun firstCheck(): Boolean

    /**
     * ログイン
     *
     */
    suspend fun signIn(email: Email, password: Password)

    /**
     * ログアウト
     *
     */
    suspend fun signOut()

    /**
     * 新規登録
     *
     */
    suspend fun signUp(email: Email, password: Password)

    /**
     * アカウント削除
     *
     */
    suspend fun delete()

    // endregion
}