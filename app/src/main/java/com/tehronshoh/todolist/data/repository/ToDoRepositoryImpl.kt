package com.tehronshoh.todolist.data.repository

import androidx.lifecycle.LiveData
import com.tehronshoh.todolist.domain.repository.ToDoRepository
import com.tehronshoh.todolist.data.model.ToDo
import com.tehronshoh.todolist.data.db.ToDoDao
import com.tehronshoh.todolist.data.db.ToDoHistoryDao
import com.tehronshoh.todolist.data.model.ToDoHistory
import com.tehronshoh.todolist.presentation.util.getDateString
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import javax.inject.Inject

class ToDoRepositoryImpl @Inject constructor(
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

    override fun getAllToDos(): Flow<List<ToDo>> = toDoDao.getAll()
}