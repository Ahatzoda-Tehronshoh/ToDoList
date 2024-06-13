package com.tehronshoh.todolist.presentation.util.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tehronshoh.todolist.data.datasource.LocalDataSource
import com.tehronshoh.todolist.data.repository.LoggedInUserRepositoryImpl
import com.tehronshoh.todolist.data.repository.UserRepositoryImpl
import com.tehronshoh.todolist.presentation.ui.authorization.signup.SignUpViewModel

class SignUpViewModelFactory(private val todoDataSource: LocalDataSource) : ViewModelProvider.Factory {
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