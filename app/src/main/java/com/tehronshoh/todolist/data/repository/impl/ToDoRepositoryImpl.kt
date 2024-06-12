package com.tehronshoh.todolist.data.repository.impl

import androidx.lifecycle.LiveData
import com.tehronshoh.todolist.data.repository.ToDoRepository
import com.tehronshoh.todolist.data.model.ToDo
import com.tehronshoh.todolist.data.ToDoDao
import com.tehronshoh.todolist.data.ToDoHistoryDao
import com.tehronshoh.todolist.data.model.ToDoHistory
import java.util.Calendar

class ToDoRepositoryImpl(
    private val toDoDao: ToDoDao,
    private val toDoHistoryDao: ToDoHistoryDao
) : ToDoRepository {
    override suspend fun createToDo(toDo: ToDo) = toDoDao.create(toDo)

    override suspend fun updateToDo(newToDo: ToDo, oldToDo: ToDo) {
        toDoDao.update(newToDo)
        if (newToDo.status != oldToDo.status) {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            toDoHistoryDao.create(
                ToDoHistory(
                    todoId = newToDo.id,
                    newStatus = newToDo.status,
                    changedAt = "$day-${month + 1}-$year"
                )
            )
        }
    }

    override suspend fun deleteToDo(toDo: ToDo) = toDoDao.delete(toDo)

    override fun getAllToDos(): LiveData<List<ToDo>> = toDoDao.getAll()
}