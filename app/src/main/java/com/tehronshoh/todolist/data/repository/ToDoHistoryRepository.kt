package com.tehronshoh.todolist.data.repository

import androidx.lifecycle.LiveData
import com.tehronshoh.todolist.data.model.ToDoHistory

interface ToDoHistoryRepository {
    fun getToDoHistory(toDoId: Long): LiveData<List<ToDoHistory>>
}