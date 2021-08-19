package com.myapp.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

/**
 * 憲法テーブル
 *
 * @property id ID
 * @property text 憲法
 * @property createAt 登録日
 */
@Entity(tableName = "constitution")
class ConstitutionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "create_at") val createAt: LocalDateTime,
)