package com.tehronshoh.todolist.data.repository.impl

import androidx.lifecycle.LiveData
import com.tehronshoh.todolist.data.repository.ToDoRepository
import com.tehronshoh.todolist.data.model.ToDo
import com.tehronshoh.todolist.data.ToDoDao
import com.tehronshoh.todolist.data.ToDoHistoryDao
import com.tehronshoh.todolist.data.model.ToDoHistory
import com.tehronshoh.todolist.presenter.util.getDateString
import java.util.Calendar

class ToDoRepositoryImpl(
    private val toDoDao: ToDoDao,
    private val toDoHistoryDao: ToDoHistoryDao
) : ToDoRepository {
    override suspend fun createToDo(toDo: ToDo) = toDoDao.create(toDo)

    override suspend fun updateToDo(newToDo: ToDo, oldToDo: ToDo) {
        toDoDao.update(newToDo)
        if (newToDo.status != oldToDo.status) {
            toDoHistoryDao.create(
                ToDoHistory(
                    todoId = newToDo.id,
                    newStatus = newToDo.status,
                    changedAt = Calendar.getInstance().getDateString()
                )
            )
        }
    }

    override suspend fun deleteToDo(toDo: ToDo) = toDoDao.delete(toDo)

    override fun getAllToDos(): LiveData<List<ToDo>> = toDoDao.getAll()
}