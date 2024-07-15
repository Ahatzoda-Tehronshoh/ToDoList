package com.tehronshoh.todolist.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.tehronshoh.todolist.data.model.ToDo
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ToDoDao {
    @Insert
    abstract suspend fun create(toDo: ToDo)

    @Update
    abstract suspend fun update(toDo: ToDo)

    @Delete
    abstract suspend fun delete(toDo: ToDo)

    @Query("SELECT * FROM todo")
    abstract fun getAll(): Flow<List<ToDo>>
}