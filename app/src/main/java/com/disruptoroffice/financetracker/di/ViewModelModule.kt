package com.disruptoroffice.financetracker.di

import com.disruptoroffice.financetracker.data.repositories.FinanceRecordRepository
import com.disruptoroffice.financetracker.data.repositories.LoginRepository
import com.disruptoroffice.financetracker.data.repositories.RegisterRepository
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

    @Provides
    fun provideRegisterRepository(): RegisterRepository {
        return RegisterRepository()
    }

    @Provides
    fun provideFinanceRecordRepository(): FinanceRecordRepository {
        return FinanceRecordRepository()
    }
}