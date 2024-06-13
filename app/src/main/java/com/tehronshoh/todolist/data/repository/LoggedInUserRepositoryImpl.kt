package com.tehronshoh.todolist.data.repository

import androidx.lifecycle.LiveData
import com.tehronshoh.todolist.data.db.LoggedInUserDao
import com.tehronshoh.todolist.data.model.LoggedInUser
import com.tehronshoh.todolist.data.model.User
import com.tehronshoh.todolist.domain.repository.LoggedInUserRepository

class LoggedInUserRepositoryImpl(private val loggedInUserDao: LoggedInUserDao):
    LoggedInUserRepository {
    override fun getLoggedInUser(): LiveData<User> = loggedInUserDao.getLoggedInUser()

    override fun userLoggedIn(user: LoggedInUser) = loggedInUserDao.userLoggedIn(user)

    override fun userLoggedOut(user: LoggedInUser) = loggedInUserDao.userLoggedOut(user)
}