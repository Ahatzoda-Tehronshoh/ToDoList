package com.tehronshoh.todolist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tehronshoh.todolist.data.model.User

@Dao
abstract class UserDao {
    @Insert
    abstract suspend fun create(user: User): Long

    @Query("SELECT * FROM user WHERE email = :email")
    abstract fun isUserWithEmailExists(email: String): User?

    @Query("SELECT * FROM user WHERE email = :email AND password = :password")
    abstract suspend fun isUserExist(email: String, password: String): User?
}