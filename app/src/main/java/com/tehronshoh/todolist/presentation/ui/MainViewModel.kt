package com.tehronshoh.todolist.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tehronshoh.todolist.data.model.LoggedInUser
import com.tehronshoh.todolist.data.model.ToDo
import com.tehronshoh.todolist.data.model.User
import com.tehronshoh.todolist.domain.repository.LoggedInUserRepository
import com.tehronshoh.todolist.domain.repository.ToDoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val toDoRepository: ToDoRepository,
    private val loggedInUserRepository: LoggedInUserRepository
) : ViewModel() {
    private var _loggedInUser: LiveData<User>? = null
    val loggedInUser: LiveData<User>
        get() = _loggedInUser!!

    private var _toDos: MutableStateFlow<List<ToDo>> = MutableStateFlow(emptyList())
    val toDos: StateFlow<List<ToDo>>
        get() = _toDos

    init {
        initValues()
    }

    private fun initValues() {
        viewModelScope.launch {
            toDoRepository.getAllToDos().collect {
                _toDos.emit(it)
            }
        }

        _loggedInUser = loggedInUserRepository.getLoggedInUser()
    }

    fun logOut() {
        viewModelScope.launch(Dispatchers.IO) {
            if (loggedInUser.value != null)
                loggedInUserRepository.userLoggedOut(LoggedInUser(loggedInUser.value!!.id))
        }
    }

    fun createToDo(toDo: ToDo) {
        viewModelScope.launch(Dispatchers.IO) {
            toDoRepository.createToDo(toDo)
        }
    }

    fun updateToDo(newToDo: ToDo, oldToDo: ToDo) {
        viewModelScope.launch(Dispatchers.IO) {
            toDoRepository.updateToDo(newToDo, oldToDo)
        }
    }

    fun deleteToDo(toDo: ToDo) {
        viewModelScope.launch(Dispatchers.IO) {
            toDoRepository.deleteToDo(toDo)
        }
    }
}