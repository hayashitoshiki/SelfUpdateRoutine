package com.myapp.data.remote.api

import com.myapp.data.local.database.entity.report.FfsReportEntity
import com.myapp.data.local.database.entity.report.WeatherReportEntity
import com.myapp.domain.model.entity.Account
import com.myapp.domain.model.value.Email
import com.myapp.domain.model.value.Password
import java.time.LocalDateTime

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
     * アカウント情報取得
     *
     * @return アカウント情報
     */
    fun getAccountDetail() : Account?

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
    suspend fun signUp(email: Email, password: Password)

    /**
     * アカウント削除
     *
     */
    suspend fun delete()

    // endregion

    // region データベースアクセス

    // FFS式４行日記テーブル１件追加
    suspend fun addFfs(ffsEntity: FfsReportEntity, email: String)

    // FFS式４行日記テーブル全件追加
    suspend fun addFfsAll(ffsEntityList: List<FfsReportEntity>, email: String)

    // FFS式４行日記テーブル全件取得
    suspend fun getFfsAll(email: String) : List<Map<String, Any>>

    // 指定日以降に登録されたのFFS式４行日記テーブル取得
    suspend fun getFfsByAfterDate(email: String, date: LocalDateTime) : List<Map<String, Any>>

    // 感情日記データ１件登録
    suspend fun addWeather(weatherEntity: WeatherReportEntity, email: String)

    // 感情日記データ全件登録
    suspend fun addWeatherAll(weatherEntityList: List<WeatherReportEntity>, email: String)

    // 感情日記データ全件取得
    suspend fun getWeatherAll(email: String) : List<Map<String, Any>>

    // 感情日記データ全件取得
    suspend fun getWeatherByAfterDate(email: String) : List<Map<String, Any>>

    suspend fun getWeatherByAfterDate(email: String, date: LocalDateTime) : List<Map<String, Any>>

    // endregion
}