package com.tehronshoh.todolist.data.repository.impl

import androidx.lifecycle.LiveData
import com.tehronshoh.todolist.data.ToDoCommentsDao
import com.tehronshoh.todolist.data.model.ToDoComments
import com.tehronshoh.todolist.data.repository.ToDoCommentsRepository

class ToDoCommentsRepositoryImpl(
    private val toDoCommentsDao: ToDoCommentsDao
): ToDoCommentsRepository {
    override fun getCommentsToDoById(id: Long): LiveData<List<ToDoComments>> = toDoCommentsDao.getCommentsToDoById(id)

    override suspend fun insert(toDoComments: ToDoComments) = toDoCommentsDao.insert(toDoComments)
}