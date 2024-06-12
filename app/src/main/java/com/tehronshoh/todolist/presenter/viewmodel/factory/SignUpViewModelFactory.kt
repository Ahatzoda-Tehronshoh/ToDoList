package com.tehronshoh.todolist.presenter.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tehronshoh.todolist.data.ToDoDataSource
import com.tehronshoh.todolist.data.repository.impl.LoggedInUserRepositoryImpl
import com.tehronshoh.todolist.data.repository.impl.UserRepositoryImpl
import com.tehronshoh.todolist.presenter.authorization.signup.SignUpViewModel

class SignUpViewModelFactory(private val todoDataSource: ToDoDataSource) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel(
                UserRepositoryImpl(todoDataSource.getUserDao()),
                LoggedInUserRepositoryImpl(todoDataSource.getLoggedInUserDao())
            ) as T
        }
        return super.create(modelClass)
    }
}