package com.tehronshoh.todolist.presentation.util.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tehronshoh.todolist.presentation.ui.MainViewModel
import com.tehronshoh.todolist.data.datasource.LocalDataSource
import com.tehronshoh.todolist.data.repository.LoggedInUserRepositoryImpl
import com.tehronshoh.todolist.data.repository.ToDoRepositoryImpl

class MainViewModelFactory(private val todoDataSource: LocalDataSource) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                ToDoRepositoryImpl(todoDataSource.getToDoDao(), todoDataSource.getToDoHistoryDao()),
                LoggedInUserRepositoryImpl(todoDataSource.getLoggedInUserDao())
            ) as T
        }
        return super.create(modelClass)
    }
}