package com.tehronshoh.todolist.data.repository.impl

import androidx.lifecycle.LiveData
import com.tehronshoh.todolist.data.repository.ToDoRepository
import com.tehronshoh.todolist.data.ToDo
import com.tehronshoh.todolist.data.ToDoDao

class ToDoRepositoryImpl(private val toDoDao: ToDoDao): ToDoRepository {
    override suspend fun createToDo(toDo: ToDo) = toDoDao.create(toDo)

    override suspend fun updateToDo(toDo: ToDo) = toDoDao.update(toDo)

    override suspend fun deleteToDo(toDo: ToDo) = toDoDao.delete(toDo)

    override fun getAllToDos(): LiveData<List<ToDo>> = toDoDao.getAll()
}