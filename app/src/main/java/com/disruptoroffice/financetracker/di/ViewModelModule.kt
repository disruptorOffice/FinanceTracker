package com.disruptoroffice.financetracker.di

import com.disruptoroffice.financetracker.data.repositories.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun provideLoginRepository(): LoginRepository {
        return LoginRepository()
    }
}