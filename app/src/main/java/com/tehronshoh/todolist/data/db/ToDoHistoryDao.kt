package com.tehronshoh.todolist.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tehronshoh.todolist.data.model.ToDoHistory

@Dao
abstract class ToDoHistoryDao {
    @Query("SELECT * FROM todo_history WHERE todo_id = :toDoId")
    abstract fun getToDoHistoryById(toDoId: Long): LiveData<List<ToDoHistory>>

    @Insert
    abstract suspend fun create(toDoHistory: ToDoHistory)
}