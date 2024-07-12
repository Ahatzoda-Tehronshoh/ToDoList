package com.tehronshoh.todolist.presentation.ui.add_update

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tehronshoh.todolist.data.model.ToDoComments
import com.tehronshoh.todolist.data.model.ToDoHistory
import com.tehronshoh.todolist.domain.repository.ToDoCommentsRepository
import com.tehronshoh.todolist.domain.repository.ToDoHistoryRepository
import com.tehronshoh.todolist.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
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