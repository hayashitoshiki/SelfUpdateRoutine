package com.myapp.data.remote.api

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
    suspend fun signIn(email: String, password: String)

    /**
     * ログアウト
     *
     */
    suspend fun signOut()

    /**
     * 新規登録
     *
     */
    suspend fun signUp(email: String, password: String)

    /**
     * アカウント削除
     *
     */
    suspend fun delete()

    // endregion
}