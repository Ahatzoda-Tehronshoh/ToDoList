package com.tehronshoh.todolist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
abstract class ToDoDao {
    @Insert
    abstract suspend fun create(toDo: ToDo)

    @Update
    abstract suspend fun update(toDo: ToDo)

    @Delete
    abstract suspend fun delete(toDo: ToDo)

    @Query("SELECT * FROM todo")
    abstract fun getAll(): LiveData<List<ToDo>>
}