package com.tehronshoh.todolist.presenter.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tehronshoh.todolist.data.model.ToDoComments
import com.tehronshoh.todolist.data.model.ToDoHistory
import com.tehronshoh.todolist.data.repository.ToDoCommentsRepository
import com.tehronshoh.todolist.data.repository.ToDoHistoryRepository
import com.tehronshoh.todolist.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditViewModel(
    private val userRepository: UserRepository,
    private val toDoHistoryRepository: ToDoHistoryRepository,
    private val toDoCommentsRepository: ToDoCommentsRepository
) : ViewModel() {
    fun getAllUsersEmail() = userRepository.getAllUsersEmail()

    fun getToDoHistory(toDoId: Long): LiveData<List<ToDoHistory>> =
        toDoHistoryRepository.getToDoHistory(toDoId)

    fun insertToDoComments(toDoComments: ToDoComments) = viewModelScope.launch(Dispatchers.IO) {
        toDoCommentsRepository.insert(toDoComments)
    }

    fun getAllToDoComments(toDoId: Long): LiveData<List<ToDoComments>> =
        toDoCommentsRepository.getCommentsToDoById(toDoId)
}