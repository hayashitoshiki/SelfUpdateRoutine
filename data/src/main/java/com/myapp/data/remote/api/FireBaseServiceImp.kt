package com.myapp.data.remote.api

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

// Firebaseアクセス
class FireBaseServiceImp @Inject constructor(private val auth: FirebaseAuth) : FireBaseService {

    // region 認証

    // 自動ログイン認証
    override fun firstCheck(): Boolean {
        return auth.currentUser != null
    }

    // ログイン機能
    override suspend fun signIn(email: String, password: String) = suspendCoroutine<Unit> { continuation ->
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                continuation.resume(Unit)
            } else {
                val exception = task.exception ?: IllegalAccessError("ログイン失敗")
                continuation.resumeWithException(exception)
            }
        }
    }

    // ログアウト
    override suspend fun signOut() {
        auth.signOut()
    }

    // アカウント作成
    override suspend fun signUp(email: String, password: String) = suspendCoroutine<Unit> { continuation ->
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                continuation.resume(Unit)
            } else {
                val exception = task.exception ?: IllegalAccessError("アカウント作成失敗")
                continuation.resumeWithException(exception)
            }
        }
    }

    // アカウント削除
    override suspend fun delete() = suspendCoroutine<Unit> { continuation ->
        auth.currentUser?.let {
            it.delete().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(Unit)
                } else {
                    val exception = task.exception ?: IllegalAccessError("アカウント削除失敗")
                    continuation.resumeWithException(exception)
                }
            }
        } ?: run { throw IllegalAccessError("アカウント削除失敗") }
    }

    // endregion
}