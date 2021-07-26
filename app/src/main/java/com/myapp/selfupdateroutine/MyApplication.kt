package com.myapp.selfupdateroutine

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.myapp.data.local.LocalReportRepositoryImp
import com.myapp.domain.repository.LocalReportRepository
import com.myapp.domain.usecase.ReportUseCase
import com.myapp.domain.usecase.ReportUseCaseImp
import com.myapp.presentation.home.ui.home.HomeViewModel
import com.myapp.presentation.home.ui.slideshow.SlideshowViewModel
import com.myapp.selfupdateroutine.database.AppDatabase
import kotlinx.coroutines.MainScope
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

class MyApplication : Application() {

    companion object {
        lateinit var shared: MyApplication
        lateinit var database: AppDatabase
        const val TAG = "MyApplication"
    }

    // Grovalp Scope
    val applicationScope = MainScope()

    init {
        shared = this
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate")
        startKoin {
            androidContext(applicationContext)
            modules(module)
        }
        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app_database")
            .build()
    }

    // Koinモジュール
    private val module: Module = module {
        viewModel { SlideshowViewModel(get()) }
        viewModel { HomeViewModel(get()) }

        // UseCase
        factory<ReportUseCase> { ReportUseCaseImp(get()) }

        // Repository
        factory<LocalReportRepository> { LocalReportRepositoryImp(database.emotionReportDao(), database.ffsReportDao()) }
    }
}
