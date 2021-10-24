package com.myapp.data.remote.api

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.myapp.data.local.database.entity.report.FfsReportEntity
import com.myapp.data.local.database.entity.report.WeatherReportEntity
import com.myapp.domain.model.entity.Account
import com.myapp.domain.model.value.Email
import com.myapp.domain.model.value.Password
import timber.log.Timber
import java.time.LocalDateTime
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

    // アカウント情報取得
    override fun getAccountDetail() : Account? {
        val account = auth.currentUser ?: return null
        return Account(account.displayName ?: "", account.email ?: "")
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
    override suspend fun signUp(email: Email, password: Password) = suspendCoroutine<Unit> { continuation ->
        auth.createUserWithEmailAndPassword(email.value, password.value).addOnCompleteListener { task ->
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

    // region DBアクセス

    // Database
    private val db = Firebase.firestore

    private enum class Table(val tableName: String) {
        WEATHER_TABLE("emotion_report"),
        FFS_TABLE("ffs_report")
    }

    // FFS式４行日記テーブル１件追加
    override suspend fun addFfs(ffsEntity: FfsReportEntity, email: String) {
        addData(Table.FFS_TABLE, ffsEntity.toFireBaseData(email))
    }

    // FFS式４行日記テーブル全件追加
    override suspend fun addFfsAll(ffsEntityList: List<FfsReportEntity>, email: String) {
        ffsEntityList.forEach{
            addData(Table.FFS_TABLE,  it.toFireBaseData(email))
        }
    }

    // FFS式４行日記テーブル全件取得
    override suspend fun getFfsAll(email: String) : List<Map<String, Any>> {
        return getDataAll(Table.FFS_TABLE, email)
    }

    // 指定日以降に登録されたのFFS式４行日記テーブル取得
    override suspend fun getFfsByAfterDate(email: String, date: LocalDateTime) : List<Map<String, Any>> {
        return getDataByAlreadyDate(Table.FFS_TABLE, email, date)
    }

    // 感情日記データ１件登録
    override suspend fun addWeather(weatherEntity: WeatherReportEntity, email: String){
        addData(Table.WEATHER_TABLE, weatherEntity.toFireBaseData(email))
    }

    // 感情日記データ全件登録
    override suspend fun addWeatherAll(weatherEntityList: List<WeatherReportEntity>, email: String) {
        weatherEntityList.forEach{
            addData(Table.WEATHER_TABLE,  it.toFireBaseData(email))
        }
    }

    // 感情日記データ全件取得
    override suspend fun getWeatherAll(email: String) : List<Map<String, Any>> {
        return getDataAll(Table.WEATHER_TABLE, email)
    }

    // 感情日記データ全件取得
    override suspend fun getWeatherByAfterDate(email: String) : List<Map<String, Any>> {
        return getDataAll(Table.WEATHER_TABLE, email)
    }

    // データ登録
    private suspend fun addData(table: Table, data: HashMap<String,Any>) = suspendCoroutine<Unit> { continuation ->
        db.collection(table.tableName)
            .add(data)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(Unit)
                } else {
                    val exception = task.exception ?: IllegalAccessError("データ登録失敗")
                    continuation.resumeWithException(exception)
                }
            }
    }

    // データ全権取得
    private suspend fun getDataAll(table: Table, email: String) = suspendCoroutine<List<Map<String, Any>>> { continuation ->
        db.collection(table.tableName)
            .whereEqualTo("email", email)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Timber.d("table:" + table.tableName+ "redsult:" + task.result)
                    val resultDat = task.result
                        ?.map { document ->
                            Timber.d("data[]:" + document.data)
                            document.data }
                        ?.toList()
                            ?: listOf<Map<String,Any>>()
                    continuation.resume(resultDat)
                } else {
                    val exception = task.exception ?: IllegalAccessError("データ取得失敗")
                    continuation.resumeWithException(exception)
                }
            }
    }

    // 指定日以降のデータリスト取得
    private suspend fun getDataByAlreadyDate(table: Table, email: String, createAt: LocalDateTime) = suspendCoroutine<List<Map<String, Any>>> { continuation ->
        db.collection(table.tableName)
          //  .where("create_time", ">=", createAt)
            .whereEqualTo("email", email)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val resultDat = task.result
                        ?.map { document -> document.data }
                        ?.toList()
                            ?: listOf<Map<String,Any>>()
                    continuation.resume(resultDat)
                } else {
                    val exception = task.exception ?: IllegalAccessError("データ取得失敗")
                    continuation.resumeWithException(exception)
                }
            }
    }


    // endregion
}