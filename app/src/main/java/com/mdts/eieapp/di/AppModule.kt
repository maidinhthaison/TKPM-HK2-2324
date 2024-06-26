package com.mdts.eieapp.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mdts.eieapp.config.AppConfig
import com.mdts.eieapp.data.network.ConnectivityDataSource
import com.mdts.eieapp.data.retrofit.RetrofitManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().setLenient().create()
    }

    @Provides
    @Singleton
    fun provideConnectivityDataSource(
        @ApplicationContext context: Context
    ): ConnectivityDataSource {
        return ConnectivityDataSource(
            applicationContext = context
        )
    }

    @DefaultApiQualifier
    @Provides
    fun provideRetrofitManager(
        gson: Gson, connectivityDataSource: ConnectivityDataSource
    ): RetrofitManager {
        return RetrofitManager(
            gson = gson,
            connectivityDataSource = connectivityDataSource,
            openAiBaseUrl = AppConfig.backendEnvironment.openAiBaseUrl,
            openAiAPIKey = AppConfig.backendEnvironment.openAiAPIKey
        )
    }

    @DictionaryApiQualifier
    @Provides
    fun provideDictionaryRetrofitManager(
        gson: Gson, connectivityDataSource: ConnectivityDataSource
    ): RetrofitManager {
        return RetrofitManager(
            gson = gson,
            connectivityDataSource = connectivityDataSource,
            openAiBaseUrl = AppConfig.backendEnvironment.dictionaryBaseUrl
        )
    }
}