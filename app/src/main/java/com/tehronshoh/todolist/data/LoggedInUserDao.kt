package com.tehronshoh.todolist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.tehronshoh.todolist.data.model.LoggedInUser
import com.tehronshoh.todolist.data.model.User

@Dao
abstract class LoggedInUserDao {
    @Query("SELECT user.* FROM user INNER JOIN logged_in_user ON user.id = logged_in_user.userId;")
    abstract fun getLoggedInUser(): LiveData<User>

    @Insert
    abstract fun userLoggedIn(user: LoggedInUser): Long

    @Delete
    abstract fun userLoggedOut(user: LoggedInUser)
}