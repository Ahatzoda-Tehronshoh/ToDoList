package com.tehronshoh.todolist.presentation.util.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tehronshoh.todolist.data.datasource.LocalDataSource
import com.tehronshoh.todolist.data.repository.ToDoCommentsRepositoryImpl
import com.tehronshoh.todolist.data.repository.ToDoHistoryRepositoryImpl
import com.tehronshoh.todolist.data.repository.UserRepositoryImpl
import com.tehronshoh.todolist.presentation.ui.add_update.EditViewModel

class EditViewModelFactory(private val todoDataSource: LocalDataSource) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditViewModel::class.java)) {
            return EditViewModel(
                UserRepositoryImpl(todoDataSource.getUserDao()),
                ToDoHistoryRepositoryImpl(todoDataSource.getToDoHistoryDao()),
                ToDoCommentsRepositoryImpl(todoDataSource.getToDoCommentsDao())
            ) as T
        }
        return super.create(modelClass)
    }
}