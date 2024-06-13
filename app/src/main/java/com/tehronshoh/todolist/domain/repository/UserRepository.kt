package com.tehronshoh.todolist.domain.repository

import androidx.lifecycle.LiveData
import com.tehronshoh.todolist.data.model.ToDo
import com.tehronshoh.todolist.data.model.User

interface UserRepository {
    suspend fun createUser(user: User): Long
    suspend fun isUserWithEmailExists(email: String): User?
    suspend fun isUserExist(email: String, password: String): User?

    fun getAllUsersEmail(): LiveData<List<String>>
}