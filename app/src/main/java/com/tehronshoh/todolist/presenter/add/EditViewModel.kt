package com.tehronshoh.todolist.presenter.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tehronshoh.todolist.data.model.ToDoHistory
import com.tehronshoh.todolist.data.repository.ToDoHistoryRepository
import com.tehronshoh.todolist.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditViewModel(
    private val userRepository: UserRepository,
    private val toDoHistoryRepository: ToDoHistoryRepository
) : ViewModel() {
    fun getAllUsersEmail() = userRepository.getAllUsersEmail()

    fun getToDoHistory(toDoId: Long): LiveData<List<ToDoHistory>> =
        toDoHistoryRepository.getToDoHistory(toDoId)
}