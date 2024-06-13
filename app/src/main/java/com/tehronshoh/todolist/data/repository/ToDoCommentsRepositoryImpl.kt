package com.tehronshoh.todolist.data.repository

import androidx.lifecycle.LiveData
import com.tehronshoh.todolist.data.db.ToDoCommentsDao
import com.tehronshoh.todolist.data.model.ToDoComments
import com.tehronshoh.todolist.domain.repository.ToDoCommentsRepository

class ToDoCommentsRepositoryImpl(
    private val toDoCommentsDao: ToDoCommentsDao
): ToDoCommentsRepository {
    override fun getCommentsToDoById(id: Long): LiveData<List<ToDoComments>> = toDoCommentsDao.getCommentsToDoById(id)

    override suspend fun insert(toDoComments: ToDoComments) = toDoCommentsDao.insert(toDoComments)
}