package com.myapp.selfupdateroutine.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.myapp.chatmemo.database.DataBaseConverter
import com.myapp.data.database.dao.EmotionReportDao
import com.myapp.data.database.dao.FfsReportDao
import com.myapp.data.database.entity.EmotionsReportEntity
import com.myapp.data.database.entity.FfsReportEntity

/**
 * DB定義
 */
@Database(
    entities = [EmotionsReportEntity::class, FfsReportEntity::class], version = 2, exportSchema = false
)
@TypeConverters(DataBaseConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun emotionReportDao(): EmotionReportDao
    abstract fun ffsReportDao(): FfsReportDao
}
