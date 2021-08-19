package com.myapp.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.myapp.data.local.database.entity.FuneralEntity

/**
 * 理想の葬儀用クエリ管理
 *
 */
@Dao
interface FuneralDao {

    @Insert
    suspend fun insert(funeralEntity: FuneralEntity)

    @Query("SELECT * FROM funeral")
    suspend fun getAll(): List<FuneralEntity>

    @Query("DELETE FROM funeral")
    suspend fun deleteAll()
}