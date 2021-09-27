package com.myapp.selfupdateroutine.di

import android.content.Context
import androidx.room.Room
import com.myapp.data.local.database.AppDatabase
import com.myapp.data.local.database.dao.mission_statement.ConstitutionDao
import com.myapp.data.local.database.dao.mission_statement.FuneralDao
import com.myapp.data.local.database.dao.mission_statement.PurposeLifeDao
import com.myapp.data.local.database.dao.report.FfsReportDao
import com.myapp.data.local.database.dao.report.WeatherReportDao
import com.myapp.selfupdateroutine.MyApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DbComponentModule {

    // Database
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext, AppDatabase::class.java, "app_database"
        )
            .build()
    }

    // Dao
    @Provides
    @Singleton
    fun provideConstitutionDao(db: AppDatabase): ConstitutionDao {
        return db.constitutionDao()
    }

    @Provides
    @Singleton
    fun provideFuneralDao(db: AppDatabase): FuneralDao {
        return db.funeralDao()
    }

    @Provides
    @Singleton
    fun providePurposeLifeDao(db: AppDatabase): PurposeLifeDao {
        return db.purposeLifeDao()
    }

    @Provides
    @Singleton
    fun provideFfsReportDao(db: AppDatabase): FfsReportDao {
        return db.ffsReportDao()
    }

    @Provides
    @Singleton
    fun provideWeatherReportDao(db: AppDatabase): WeatherReportDao {
        return db.emotionReportDao()
    }

    // coroutine
    @Provides
    fun provideCoroutineScope(): CoroutineScope {
        return MyApplication.shared.applicationScope
    }

    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.Default
    }
}
