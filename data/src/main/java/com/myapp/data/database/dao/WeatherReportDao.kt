package com.myapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.myapp.data.database.entity.WeatherReportEntity

/**
 * 感情日記用クエリ管理
 */
@Dao
interface WeatherReportDao {

    @Insert
    suspend fun insert(weatherReportEntity: WeatherReportEntity)

    @Query("SELECT * FROM emotion_report")
    suspend fun getAll(): Array<WeatherReportEntity>
}
