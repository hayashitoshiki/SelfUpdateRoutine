package com.myapp.selfupdateroutine.di

import android.content.Context
import com.myapp.data.local.preferences.PreferenceManager
import com.myapp.data.local.preferences.UserSharedPreferences
import com.myapp.data.local.preferences.UserSharedPreferencesImp
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferencesComponentModule {

    @Binds
    abstract fun bindUserSharedPreferencesImp(userSharedPreferencesImp: UserSharedPreferencesImp): UserSharedPreferences
}

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun providePreferenceManager(@ApplicationContext context: Context): PreferenceManager {
        return PreferenceManager(context)
    }
}
