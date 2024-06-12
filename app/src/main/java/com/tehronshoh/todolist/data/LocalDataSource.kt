package com.tehronshoh.todolist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tehronshoh.todolist.data.model.LoggedInUser
import com.tehronshoh.todolist.data.model.ToDo
import com.tehronshoh.todolist.data.model.ToDoHistory
import com.tehronshoh.todolist.data.model.User

@Database(
    entities = [ToDo::class, User::class, LoggedInUser::class, ToDoHistory::class],
    version = 1,
    exportSchema = false
)
abstract class LocalDataSource : RoomDatabase() {
    abstract fun getToDoDao(): ToDoDao
    abstract fun getUserDao(): UserDao
    abstract fun getLoggedInUserDao(): LoggedInUserDao
    abstract fun getToDoHistoryDao(): ToDoHistoryDao

    companion object {
        @Volatile
        private var toDoDataSourceInstance: LocalDataSource? = null

        fun getInstance(context: Context): LocalDataSource {
            return synchronized(this) {
                toDoDataSourceInstance ?: Room.databaseBuilder(
                    context,
                    LocalDataSource::class.java,
                    "database"
                )
                    .build().also {
                        toDoDataSourceInstance = it
                    }
            }
        }
    }
}