package com.myapp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

/**
 * FFS式４行日記_テーブル
 */
@Entity(tableName = "ffs_report")
data class FfsReportEntity(

    /**
     * ID
     */
    @PrimaryKey(autoGenerate = true) val id: Long?,

    /**
     * 事実
     */
    @ColumnInfo(name = "fact_comment") val factComment: String,

    /**
     * 発見
     */
    @ColumnInfo(name = "find_comment") val findComment: String,

    /**
     * 学び
     */
    @ColumnInfo(name = "learn_comment") val learnComment: String,

    /**
     * 宣言
     */
    @ColumnInfo(name = "statement_comment") val statementComment: String,

    /**
     * 生成日時
     */
    @ColumnInfo(name = "create_time") val createTime: LocalDateTime
)