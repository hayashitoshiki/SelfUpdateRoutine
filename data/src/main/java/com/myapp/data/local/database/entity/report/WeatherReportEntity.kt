package com.myapp.data.local.database.entity.report

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

/**
 * 感情日記_テーブル
 */
@Entity(tableName = "emotion_report")
data class WeatherReportEntity(

    /**
     * ID
     */
    @PrimaryKey(autoGenerate = true) val id: Long?,

    /**
     * 今日の天気（感情）
     */
    @ColumnInfo(name = "heart_core") val heartScore: Int,

    /**
     * 理由
     */
    @ColumnInfo(name = "reason_comment") val reasonComment: String,

    /**
     * 明日への改善点
     */
    @ColumnInfo(name = "improve_comment") val improveComment: String,

    /**
     * 生成日時
     */
    @ColumnInfo(name = "create_time") val createTime: LocalDateTime
)
