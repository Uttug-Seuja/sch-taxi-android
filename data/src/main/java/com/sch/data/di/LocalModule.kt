package com.sch.data.di

import android.content.Context
import androidx.room.Room
import com.sch.data.dao.SearchHistoryDao
import com.sch.data.db.SearchHistoryDatabase
import com.sch.data.repository.SearchHistoryRepositoryImpl
import com.sch.domain.repository.SearchHistoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LocalModule {

    @Provides
    @Singleton
    fun provideTodoDataSource(todoDao: SearchHistoryDao): SearchHistoryRepository {
        return SearchHistoryRepositoryImpl(todoDao)
    }

    //room
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SearchHistoryDatabase {
        return Room.databaseBuilder(
            context,
            SearchHistoryDatabase::class.java, "sch_taxi.db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideTodoDao(searchHistoryDatabase: SearchHistoryDatabase): SearchHistoryDao {
        return searchHistoryDatabase.searchHistoryDao()
    }

}