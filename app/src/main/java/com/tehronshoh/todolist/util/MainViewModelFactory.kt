package com.tehronshoh.todolist.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tehronshoh.todolist.MainViewModel
import com.tehronshoh.todolist.data.ToDoDataSource
import com.tehronshoh.todolist.data.repository.impl.ToDoRepositoryImpl

class MainViewModelFactory(private val todoDataSource: ToDoDataSource): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(ToDoRepositoryImpl(todoDataSource.getToDoDao())) as T
        }
        return super.create(modelClass)
    }
}