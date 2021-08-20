package com.myapp.data.local.database.entity.mission_statement

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

/**
 * 理想の葬式テーブル
 *
 * @property id ID
 * @property text　理想の葬式
 * @property createAt 登録日
 */
@Entity(tableName = "funeral")
class FuneralEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "create_at") val createAt: LocalDateTime
)