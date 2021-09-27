package com.myapp.data.local.database.dao.mission_statement

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.myapp.data.local.database.entity.mission_statement.PurposeLifeEntity

/**
 * 人生の目的用クエリ管理
 *
 */
@Dao
interface PurposeLifeDao {

    @Insert
    suspend fun insert(purposeLifeEntity: PurposeLifeEntity)

    @Update
    suspend fun update(purposeLifeEntity: PurposeLifeEntity)

    @Query("SELECT * FROM purpose_life WHERE id = 1")
    suspend fun get(): PurposeLifeEntity?
}
