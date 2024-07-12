package com.tehronshoh.todolist.presentation.di

import com.tehronshoh.todolist.data.repository.LoggedInUserRepositoryImpl
import com.tehronshoh.todolist.data.repository.ToDoCommentsRepositoryImpl
import com.tehronshoh.todolist.data.repository.ToDoHistoryRepositoryImpl
import com.tehronshoh.todolist.data.repository.ToDoRepositoryImpl
import com.tehronshoh.todolist.data.repository.UserRepositoryImpl
import com.tehronshoh.todolist.domain.repository.LoggedInUserRepository
import com.tehronshoh.todolist.domain.repository.ToDoCommentsRepository
import com.tehronshoh.todolist.domain.repository.ToDoHistoryRepository
import com.tehronshoh.todolist.domain.repository.ToDoRepository
import com.tehronshoh.todolist.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindToDoRepository(toDoRepositoryImpl: ToDoRepositoryImpl): ToDoRepository

    @Binds
    @Singleton
    fun bindLoggedInUserRepository(loggedInUserRepositoryImpl: LoggedInUserRepositoryImpl): LoggedInUserRepository

    @Binds
    @Singleton
    fun bindToDoCommentsRepository(toDoCommentsRepositoryImpl: ToDoCommentsRepositoryImpl): ToDoCommentsRepository

    @Binds
    @Singleton
    fun bindToDoHistoryRepository(toDoHistoryRepositoryImpl: ToDoHistoryRepositoryImpl): ToDoHistoryRepository

    @Binds
    @Singleton
    fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository
}