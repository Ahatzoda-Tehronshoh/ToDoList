package com.tehronshoh.todolist.data.repository

import androidx.lifecycle.LiveData
import com.tehronshoh.todolist.data.db.ToDoHistoryDao
import com.tehronshoh.todolist.data.model.ToDoHistory
import com.tehronshoh.todolist.domain.repository.ToDoHistoryRepository
import javax.inject.Inject

class ToDoHistoryRepositoryImpl @Inject constructor(
    private val toDoHistoryDao: ToDoHistoryDao
) : ToDoHistoryRepository {
    override fun getToDoHistory(toDoId: Long): LiveData<List<ToDoHistory>> =
        toDoHistoryDao.getToDoHistoryById(toDoId)
}