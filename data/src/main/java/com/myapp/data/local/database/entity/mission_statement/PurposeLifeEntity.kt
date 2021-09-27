package com.myapp.data.local.database.entity.mission_statement

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

/**
 * 人生の目的テーブル
 *
 * @property id ID
 * @property text 人生の目的
 * @property createAt 登録日
 * @property updateAt 更新日
 */
@Entity(tableName = "purpose_life")
data class PurposeLifeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "text") var text: String,
    @ColumnInfo(name = "create_at") val createAt: LocalDateTime,
    @ColumnInfo(name = "update_at") var updateAt: LocalDateTime,
)
