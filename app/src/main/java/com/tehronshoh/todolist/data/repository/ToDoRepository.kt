package com.tehronshoh.todolist.data.repository

import androidx.lifecycle.LiveData
import com.tehronshoh.todolist.data.ToDo

interface ToDoRepository {
    suspend fun createToDo(toDo: ToDo)
    suspend fun updateToDo(toDo: ToDo)
    suspend fun deleteToDo(toDo: ToDo)
    fun getAllToDos(): LiveData<List<ToDo>>
}