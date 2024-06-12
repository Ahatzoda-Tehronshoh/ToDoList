package com.tehronshoh.todolist.presenter.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tehronshoh.todolist.presenter.viewmodel.MainViewModel
import com.tehronshoh.todolist.data.ToDoDataSource
import com.tehronshoh.todolist.data.repository.impl.LoggedInUserRepositoryImpl
import com.tehronshoh.todolist.data.repository.impl.ToDoRepositoryImpl

class MainViewModelFactory(private val todoDataSource: ToDoDataSource) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                ToDoRepositoryImpl(todoDataSource.getToDoDao()),
                LoggedInUserRepositoryImpl(todoDataSource.getLoggedInUserDao())
            ) as T
        }
        return super.create(modelClass)
    }
}