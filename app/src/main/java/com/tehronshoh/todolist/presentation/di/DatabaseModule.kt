package com.tehronshoh.todolist.presentation.di

import android.content.Context
import com.tehronshoh.todolist.data.datasource.LocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    fun provideDataStore(@ApplicationContext context: Context) =
        LocalDataSource.getInstance(context = context)

    @Provides
    fun provideToDoDao(dataStore: LocalDataSource) = dataStore.getToDoDao()

    @Provides
    fun provideUserDao(dataStore: LocalDataSource) = dataStore.getUserDao()

    @Provides
    fun provideLoggedInUserDao(dataStore: LocalDataSource) = dataStore.getLoggedInUserDao()

    @Provides
    fun provideToDoHistoryDao(dataStore: LocalDataSource) = dataStore.getToDoHistoryDao()

    @Provides
    fun provideToDoCommentsDao(dataStore: LocalDataSource) = dataStore.getToDoCommentsDao()
}