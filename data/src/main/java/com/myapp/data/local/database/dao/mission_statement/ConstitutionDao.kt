package com.myapp.data.local.database.dao.mission_statement

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.myapp.data.local.database.entity.mission_statement.ConstitutionEntity

/**
 * 憲法用クエリ管理
 *
 */
@Dao
interface ConstitutionDao {

    @Insert
    suspend fun insert(constitutionEntity: ConstitutionEntity)

    @Query("SELECT * FROM constitution")
    suspend fun getAll(): List<ConstitutionEntity>

    @Query("DELETE FROM constitution")
    suspend fun deleteAll()
}