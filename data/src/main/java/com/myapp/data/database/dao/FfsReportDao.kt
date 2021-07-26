package com.myapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.myapp.data.database.entity.FfsReportEntity

/**
 * FFS式日記用クエリ管理
 */
@Dao
interface FfsReportDao {

    @Insert
    suspend fun insert(ffsReportEntity: FfsReportEntity)


    @Query("SELECT * FROM ffs_report WHERE id = :id")
    suspend fun getReportById(id: Long): FfsReportEntity
}
