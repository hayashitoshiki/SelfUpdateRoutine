package com.myapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.myapp.data.database.entity.EmotionsReportEntity

/**
 * 感情日記用クエリ管理
 */
@Dao
interface EmotionReportDao {

    @Insert
    suspend fun insert(emotionsReportEntity: EmotionsReportEntity)


    @Query("SELECT * FROM emotion_report WHERE id = :id")
    suspend fun getReportById(id: Long): EmotionsReportEntity
}
