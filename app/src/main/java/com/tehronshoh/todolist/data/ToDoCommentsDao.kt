package com.tehronshoh.todolist.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.tehronshoh.todolist.data.model.ToDoComments

@Dao
abstract class ToDoCommentsDao {
    @Query("SELECT * FROM comments WHERE todo_id = :id")
    abstract fun getCommentsToDoById(id: Long): LiveData<List<ToDoComments>>

    @Insert
    abstract suspend fun insert(toDoComments: ToDoComments)
}