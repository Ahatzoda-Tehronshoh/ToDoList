package com.tehronshoh.todolist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ToDo::class], version = 1, exportSchema = false)
abstract class ToDoDataSource: RoomDatabase() {
    abstract fun getToDoDao(): ToDoDao

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