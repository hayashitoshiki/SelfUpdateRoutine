package com.myapp.data.remote

import com.myapp.data.remote.api.FireBaseService
import com.myapp.domain.model.value.Email
import com.myapp.domain.model.value.Password
import com.myapp.domain.repository.RemoteAccountRepository
import javax.inject.Inject

// 外部アカウント関連CRUD処理
class RemoteAccountRepositoryImp @Inject constructor(private val fireBaseService: FireBaseService): RemoteAccountRepository {

    //　自動認証
    override fun autoAuth(): Boolean = fireBaseService.firstCheck()

    // ログイン
    override suspend fun signIn(email: String, password: String) {
        fireBaseService.signIn(email, password)
    }

    // ログアウト
    override suspend fun signOut() {
        fireBaseService.signOut()
    }

    // アカウント作成
    override suspend fun signUp(email: Email, password: Password) {
        fireBaseService.signUp(email, password)
    }

    // アカウント削除
    override suspend fun delete() {
        fireBaseService.delete()
    }
}