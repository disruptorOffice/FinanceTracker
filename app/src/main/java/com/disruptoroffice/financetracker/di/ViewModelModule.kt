package com.disruptoroffice.financetracker.di

import com.disruptoroffice.financetracker.config.RetrofitClient
import com.disruptoroffice.financetracker.data.repositories.FinanceRecordRepository
import com.disruptoroffice.financetracker.data.repositories.FrequencyRepository
import com.disruptoroffice.financetracker.data.repositories.LoginRepository
import com.disruptoroffice.financetracker.data.repositories.RegisterRepository
import com.disruptoroffice.financetracker.data.repositories.TypeCategoryRepository
import com.disruptoroffice.financetracker.data.repositories.TypePaymentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun provideLoginRepository(httpClient: RetrofitClient): LoginRepository {
        return LoginRepository(httpClient)
    }

    @Provides
    fun provideRegisterRepository(httpClient: RetrofitClient): RegisterRepository {
        return RegisterRepository(httpClient)
    }

    @Provides
    fun provideFinanceRecordRepository(httpClient: RetrofitClient): FinanceRecordRepository {
        return FinanceRecordRepository(httpClient)
    }

    @Provides
    fun providesTypePaymentRepository(httpClient: RetrofitClient): TypePaymentRepository {
        return TypePaymentRepository(httpClient)
    }

    @Provides
    fun providesTypeCategoryRepository(httpClient: RetrofitClient): TypeCategoryRepository {
        return TypeCategoryRepository(httpClient)
    }

    @Provides
    fun providesFrequencyRepository(httpClient: RetrofitClient): FrequencyRepository {
        return FrequencyRepository(httpClient)
    }
}