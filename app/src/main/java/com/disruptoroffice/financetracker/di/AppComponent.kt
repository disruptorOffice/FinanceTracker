package com.disruptoroffice.financetracker.di

import android.content.Context
import com.disruptoroffice.financetracker.config.RetrofitClient
import com.disruptoroffice.financetracker.config.SessionPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppComponent {

    @Provides
    @Singleton
    fun provideSessionPreferences(@ApplicationContext context: Context): SessionPreferences {
        return SessionPreferences(context)
    }

    @Provides
    @Singleton
    fun providesRetrofitClient(session: SessionPreferences): RetrofitClient {
        return RetrofitClient(session)
    }
}