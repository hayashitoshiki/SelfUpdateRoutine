package com.myapp.data.local.database.entity.report

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

/**
 * 感情日記_テーブル
 *
 * @property id ID
 * @property heartScore 今日の天気（感情）
 * @property reasonComment 理由
 * @property improveComment 明日への改善点
 * @property createTime 生成日時
 */
@Entity(tableName = "emotion_report")
data class WeatherReportEntity(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "heart_core") val heartScore: Int,
    @ColumnInfo(name = "reason_comment") val reasonComment: String,
    @ColumnInfo(name = "improve_comment") val improveComment: String,
    @ColumnInfo(name = "create_time") val createTime: LocalDateTime
) {
    fun toFireBaseData(email: String): HashMap<String,Any>{
        return hashMapOf(
            "email" to email,
            "id" to id.toString(),
            "heart_core" to heartScore,
            "reason_comment" to reasonComment,
            "improve_comment" to improveComment,
            "create_time" to createTime
        )
    }
}
