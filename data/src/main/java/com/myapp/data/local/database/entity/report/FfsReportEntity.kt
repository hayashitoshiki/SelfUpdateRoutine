package com.myapp.data.local.database.entity.report

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

/**
 * FFS式４行日記_テーブル
 *
 * @property id ID
 * @property factComment 事実
 * @property findComment 発見
 * @property learnComment 学び
 * @property statementComment 宣言
 * @property createTime 生成日時
 */
@Entity(tableName = "ffs_report")
data class FfsReportEntity(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "fact_comment") val factComment: String,
    @ColumnInfo(name = "find_comment") val findComment: String,
    @ColumnInfo(name = "learn_comment") val learnComment: String,
    @ColumnInfo(name = "statement_comment") val statementComment: String,
    @ColumnInfo(name = "create_time") val createTime: LocalDateTime
) {
    fun toFireBaseData(email: String): HashMap<String,Any>{
        return hashMapOf(
            "email" to email,
            "id" to id.toString(),
            "fact_comment" to factComment,
            "find_comment" to findComment,
            "learn_comment" to learnComment,
            "statement_comment" to statementComment,
            "create_time" to createTime
        )
    }
}
