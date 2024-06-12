package com.tehronshoh.todolist.data.repository.impl

import androidx.lifecycle.LiveData
import com.tehronshoh.todolist.data.ToDoHistoryDao
import com.tehronshoh.todolist.data.model.ToDoHistory
import com.tehronshoh.todolist.data.repository.ToDoHistoryRepository

class ToDoHistoryRepositoryImpl(
    private val toDoHistoryDao: ToDoHistoryDao
) : ToDoHistoryRepository {
    override fun getToDoHistory(toDoId: Long): LiveData<List<ToDoHistory>> =
        toDoHistoryDao.getToDoHistoryById(toDoId)
}