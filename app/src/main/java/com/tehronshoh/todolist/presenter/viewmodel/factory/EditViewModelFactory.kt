package com.tehronshoh.todolist.presenter.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tehronshoh.todolist.data.ToDoDataSource
import com.tehronshoh.todolist.data.repository.impl.UserRepositoryImpl
import com.tehronshoh.todolist.presenter.add.EditViewModel

class EditViewModelFactory(private val todoDataSource: ToDoDataSource) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditViewModel::class.java)) {
            return EditViewModel(UserRepositoryImpl(todoDataSource.getUserDao())) as T
        }
        return super.create(modelClass)
    }
}