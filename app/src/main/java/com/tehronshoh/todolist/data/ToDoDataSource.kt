package com.tehronshoh.todolist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tehronshoh.todolist.data.model.LoggedInUser
import com.tehronshoh.todolist.data.model.ToDo
import com.tehronshoh.todolist.data.model.User

@Database(entities = [ToDo::class, User::class, LoggedInUser::class], version = 1, exportSchema = false)
abstract class ToDoDataSource: RoomDatabase() {
    abstract fun getToDoDao(): ToDoDao
    abstract fun getUserDao(): UserDao
    abstract fun getLoggedInUserDao(): LoggedInUserDao

    companion object {
        @Volatile
        private var toDoDataSourceInstance: ToDoDataSource? = null

        fun getInstance(context: Context): ToDoDataSource {
            return synchronized(this) {
                toDoDataSourceInstance ?: Room.databaseBuilder(
                    context,
                    ToDoDataSource::class.java,
                    "database"
                )
                    .build().also {
                        toDoDataSourceInstance = it
                    }
            }
        }
    }
}