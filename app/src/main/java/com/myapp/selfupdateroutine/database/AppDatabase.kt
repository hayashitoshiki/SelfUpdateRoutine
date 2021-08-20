package com.myapp.selfupdateroutine.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.myapp.chatmemo.database.DataBaseConverter
import com.myapp.data.local.database.dao.mission_statement.ConstitutionDao
import com.myapp.data.local.database.dao.mission_statement.FuneralDao
import com.myapp.data.local.database.dao.mission_statement.PurposeLifeDao
import com.myapp.data.local.database.dao.report.FfsReportDao
import com.myapp.data.local.database.dao.report.WeatherReportDao
import com.myapp.data.local.database.entity.mission_statement.ConstitutionEntity
import com.myapp.data.local.database.entity.mission_statement.FuneralEntity
import com.myapp.data.local.database.entity.mission_statement.PurposeLifeEntity
import com.myapp.data.local.database.entity.report.FfsReportEntity
import com.myapp.data.local.database.entity.report.WeatherReportEntity

/**
 * DB定義
 */
@Database(
    entities = [WeatherReportEntity::class, FfsReportEntity::class, FuneralEntity::class, PurposeLifeEntity::class, ConstitutionEntity::class],
    version = 3, exportSchema = false
)
@TypeConverters(DataBaseConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun emotionReportDao(): WeatherReportDao
    abstract fun ffsReportDao(): FfsReportDao
    abstract fun funeralDao(): FuneralDao
    abstract fun purposeLifeDao(): PurposeLifeDao
    abstract fun constitutionDao(): ConstitutionDao

    companion object {
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE `constitution` (`id` INTEGER NOT NULL, `text` TEXT NOT NULL, `create_at` TEXT  NOT NULL, PRIMARY KEY(`id`))"
                )
                database.execSQL(
                    "CREATE TABLE `purpose_life` (`id` INTEGER NOT NULL, `text` TEXT NOT NULL, `create_at` TEXT  NOT NULL, `update_at` TEXT NOT NULL, PRIMARY KEY(`id`))"
                )
                database.execSQL(
                    "CREATE TABLE `funeral` (`id` INTEGER NOT NULL, `text` TEXT NOT NULL, `create_at` TEXT  NOT NULL, PRIMARY KEY(`id`))"
                )

            }
        }
    }
}
