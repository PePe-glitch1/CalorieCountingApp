package com.example.caloriecountingapp.di

import android.content.Context
import com.example.data.local.dao.AddedProductsDao
import com.example.data.local.dao.AddedVoterDao
import com.example.data.local.dao.AllProductsDao
import com.example.data.local.dao.DailyCaloryDao
import com.example.data.local.dao.UserDao
import com.example.data.local.dao.UserParamsDao
import com.example.data.local.database.AppDatabase
import com.example.data.repository.AddedProductsRepositoryImpl
import com.example.data.repository.AddedVoterRepositoryImpl
import com.example.data.repository.AllProductsRepositoryImpl
import com.example.data.repository.DailyCaloryRepositoryImpl
import com.example.data.repository.UserParamsRepositoryImpl
import com.example.data.repository.UserRepositoryImpl
import com.example.domain.repository.AddedProductsRepository
import com.example.domain.repository.AddedVoterRepository
import com.example.domain.repository.AllProductsRepository
import com.example.domain.repository.DailyCaloryRepository
import com.example.domain.repository.UserParamsRepository
import com.example.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    // DAOs

    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    fun provideUserParamsDao(appDatabase: AppDatabase): UserParamsDao {
        return appDatabase.userParamsDao()
    }

    @Provides
    fun provideDailyCaloryDao(appDatabase: AppDatabase): DailyCaloryDao {
        return appDatabase.dailyCaloryDao()
    }

    @Provides
    fun provideAllProductsDao(appDatabase: AppDatabase): AllProductsDao {
        return appDatabase.allProductsDao()
    }

    @Provides
    fun provideAddedProductsDao(appDatabase: AppDatabase): AddedProductsDao {
        return appDatabase.addedProductsDao()
    }

    @Provides
    fun provideAddedVoterDao(appDatabase: AppDatabase): AddedVoterDao {
        return appDatabase.addedVoterDao()
    }

    // Repositories

    @Provides
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepositoryImpl(userDao)
    }

    @Provides
    fun provideUserParamsRepository(userParamsDao: UserParamsDao): UserParamsRepository {
        return UserParamsRepositoryImpl(userParamsDao)
    }

    @Provides
    fun provideDailyCaloryRepository(dailyCaloryDao: DailyCaloryDao): DailyCaloryRepository {
        return DailyCaloryRepositoryImpl(dailyCaloryDao)
    }

    @Provides
    fun provideAllProductsRepository(allProductsDao: AllProductsDao): AllProductsRepository {
        return AllProductsRepositoryImpl(allProductsDao)
    }

    @Provides
    fun provideAddedProductsRepository(addedProductsDao: AddedProductsDao): AddedProductsRepository {
        return AddedProductsRepositoryImpl(addedProductsDao)
    }

    @Provides
    fun provideAddedVoterRepository(addedVoterDao: AddedVoterDao): AddedVoterRepository {
        return AddedVoterRepositoryImpl(addedVoterDao)
    }
}
