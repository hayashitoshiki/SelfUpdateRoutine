package com.myapp.selfupdateroutine

import android.app.Application
import android.util.Log
import com.myapp.presentation.home.ui.slideshow.SlideshowViewModel
import kotlinx.coroutines.MainScope
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

class MyApplication : Application() {

    companion object {
        lateinit var shared: MyApplication

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
    }

    // Koinモジュール
    private val module: Module = module {
        viewModel { SlideshowViewModel() }
    }
}
