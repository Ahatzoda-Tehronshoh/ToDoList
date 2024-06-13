package com.tehronshoh.todolist.data.repository

import androidx.lifecycle.LiveData
import com.tehronshoh.todolist.data.db.UserDao
import com.tehronshoh.todolist.data.model.User
import com.tehronshoh.todolist.domain.repository.UserRepository

class UserRepositoryImpl(private val userDao: UserDao): UserRepository {
    override suspend fun createUser(user: User) = userDao.create(user)

    override suspend fun isUserWithEmailExists(email: String): User? = userDao.isUserWithEmailExists(email)

    override suspend fun isUserExist(email: String, password: String): User? =
        userDao.isUserExist(email, password)

    override fun getAllUsersEmail(): LiveData<List<String>> = userDao.getAllUsersEmail()
}