package com.tehronshoh.todolist.domain.repository

import androidx.lifecycle.LiveData
import com.tehronshoh.todolist.data.model.LoggedInUser
import com.tehronshoh.todolist.data.model.User

interface LoggedInUserRepository {
    fun getLoggedInUser(): LiveData<User>
    fun userLoggedIn(user: LoggedInUser): Long
    fun userLoggedOut(user: LoggedInUser)
}