package com.tehronshoh.todolist.presenter.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tehronshoh.todolist.data.LocalDataSource
import com.tehronshoh.todolist.data.repository.impl.ToDoHistoryRepositoryImpl
import com.tehronshoh.todolist.data.repository.impl.UserRepositoryImpl
import com.tehronshoh.todolist.presenter.add.EditViewModel

class EditViewModelFactory(private val todoDataSource: LocalDataSource) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditViewModel::class.java)) {
            return EditViewModel(
                UserRepositoryImpl(todoDataSource.getUserDao()),
                ToDoHistoryRepositoryImpl(todoDataSource.getToDoHistoryDao())
            ) as T
        }
        return super.create(modelClass)
    }
}