package com.tehronshoh.todolist.data.repository

import androidx.lifecycle.LiveData
import com.tehronshoh.todolist.data.model.ToDoComments

interface ToDoCommentsRepository {
    fun getCommentsToDoById(id: Long): LiveData<List<ToDoComments>>
    suspend fun insert(toDoComments: ToDoComments)
}