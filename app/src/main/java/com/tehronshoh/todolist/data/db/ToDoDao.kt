package com.tehronshoh.todolist.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.tehronshoh.todolist.data.model.ToDo

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