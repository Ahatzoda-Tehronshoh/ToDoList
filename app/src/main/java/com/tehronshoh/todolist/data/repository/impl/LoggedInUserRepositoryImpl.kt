package com.tehronshoh.todolist.data.repository.impl

import androidx.lifecycle.LiveData
import com.tehronshoh.todolist.data.LoggedInUserDao
import com.tehronshoh.todolist.data.model.LoggedInUser
import com.tehronshoh.todolist.data.model.User
import com.tehronshoh.todolist.data.repository.LoggedInUserRepository

class LoggedInUserRepositoryImpl(private val loggedInUserDao: LoggedInUserDao): LoggedInUserRepository {
    override fun getLoggedInUser(): LiveData<User> = loggedInUserDao.getLoggedInUser()

    override fun userLoggedIn(user: LoggedInUser) = loggedInUserDao.userLoggedIn(user)

    override fun userLoggedOut(user: LoggedInUser) = loggedInUserDao.userLoggedOut(user)
}