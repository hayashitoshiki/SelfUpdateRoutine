package com.myapp.data.local.database.dao.report

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.myapp.data.local.database.entity.report.FfsReportEntity

/**
 * FFS式日記用クエリ管理
 */
@Dao
interface FfsReportDao {

    @Insert
    suspend fun insert(ffsReportEntity: FfsReportEntity)

    @Query("SELECT * FROM ffs_report")
    suspend fun getAll(): Array<FfsReportEntity>

    @Query("DELETE FROM ffs_report")
    suspend fun deleteAll()

}
