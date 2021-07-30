package com.myapp.selfupdateroutine.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.myapp.chatmemo.database.DataBaseConverter
import com.myapp.data.database.dao.FfsReportDao
import com.myapp.data.database.dao.WeatherReportDao
import com.myapp.data.database.entity.FfsReportEntity
import com.myapp.data.database.entity.WeatherReportEntity

/**
 * DB定義
 */
@Database(
    entities = [WeatherReportEntity::class, FfsReportEntity::class], version = 2, exportSchema = false
)
@TypeConverters(DataBaseConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun emotionReportDao(): WeatherReportDao
    abstract fun ffsReportDao(): FfsReportDao
}
