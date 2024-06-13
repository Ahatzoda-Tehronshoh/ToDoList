package com.tehronshoh.todolist.domain.repository

import androidx.lifecycle.LiveData
import com.tehronshoh.todolist.data.model.ToDo

interface ToDoRepository {
    suspend fun createToDo(toDo: ToDo)
    suspend fun updateToDo(newToDo: ToDo, oldToDo: ToDo)
    suspend fun deleteToDo(toDo: ToDo)
    fun getAllToDos(): LiveData<List<ToDo>>
}