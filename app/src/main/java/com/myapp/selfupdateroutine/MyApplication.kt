package com.myapp.selfupdateroutine

import android.app.Application
import androidx.room.Room
import com.myapp.data.local.database.AppDatabase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.MainScope
import timber.log.Timber

@HiltAndroidApp
class MyApplication : Application() {

    companion object {
        lateinit var shared: MyApplication
        lateinit var database: AppDatabase
    }

    init {
        shared = this
    }

    // Global Scope
    val applicationScope = MainScope()

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext, AppDatabase::class.java, "app_database"
        )
            .addMigrations(AppDatabase.MIGRATION_2_3)
            .build()
        configureTimber()
        Timber.tag(this.javaClass.simpleName)
            .d("onCreate")
    }

    // Timber設定
    private fun configureTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
