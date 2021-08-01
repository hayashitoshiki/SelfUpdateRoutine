package com.myapp.selfupdateroutine

import android.app.Application
import android.util.Log
import androidx.room.Room
import com.myapp.data.local.LocalReportRepositoryImp
import com.myapp.domain.model.entity.Report
import com.myapp.domain.repository.LocalReportRepository
import com.myapp.domain.usecase.ReportUseCase
import com.myapp.domain.usecase.ReportUseCaseImp
import com.myapp.presentation.ui.diary.*
import com.myapp.presentation.ui.home.HomeViewModel
import com.myapp.presentation.ui.remember.RememberViewModel
import com.myapp.selfupdateroutine.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import timber.log.Timber

class MyApplication : Application() {

    companion object {
        lateinit var shared: MyApplication
        lateinit var database: AppDatabase
        const val TAG = "MyApplication"
    }

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
        configureTimber()
    }

    // Timber設定
    private fun configureTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    // Koinモジュール
    private val module: Module = module {
        viewModel { FfsFactViewModel() }
        viewModel { FfsFindViewModel() }
        viewModel { FfsLearnViewModel() }
        viewModel { FfsStatementViewModel() }
        viewModel { FfsResultViewModel() }
        viewModel { WeatherAssessmentViewModel() }
        viewModel { WeatherReasonViewModel() }
        viewModel { WeatherImproveViewModel() }
        viewModel { WeatherResultViewModel(get()) }

        viewModel { HomeViewModel(get()) }
        viewModel { (report: Report) -> RememberViewModel(report) }

        // UseCase
        factory<ReportUseCase> { ReportUseCaseImp(get()) }

        // Repository
        factory<LocalReportRepository> { LocalReportRepositoryImp(database.emotionReportDao(), database.ffsReportDao()) }
    }
}
