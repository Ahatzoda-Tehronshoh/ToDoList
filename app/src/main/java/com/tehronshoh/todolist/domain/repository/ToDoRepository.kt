package com.tehronshoh.todolist.domain.repository

import androidx.lifecycle.LiveData
import com.tehronshoh.todolist.data.model.ToDo
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {
    suspend fun createToDo(toDo: ToDo)
    suspend fun updateToDo(newToDo: ToDo, oldToDo: ToDo)
    suspend fun deleteToDo(toDo: ToDo)
    fun getAllToDos(): Flow<List<ToDo>>
}