package com.myapp.selfupdateroutine.di

import com.google.firebase.auth.FirebaseAuth
import com.myapp.data.remote.api.FireBaseService
import com.myapp.data.remote.api.FireBaseServiceImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceComponentModule {
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }
    @Provides
    @Singleton
    fun provideFireBaseServiceImp(firebase: FirebaseAuth): FireBaseService {
        return FireBaseServiceImp(firebase)
    }

}