package com.tehronshoh.todolist.presenter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tehronshoh.todolist.data.model.LoggedInUser
import com.tehronshoh.todolist.data.model.ToDo
import com.tehronshoh.todolist.data.model.User
import com.tehronshoh.todolist.data.repository.LoggedInUserRepository
import com.tehronshoh.todolist.data.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val toDoRepository: ToDoRepository,
    private val loggedInUserRepository: LoggedInUserRepository
) : ViewModel() {
    private var _loggedInUser: LiveData<User>? = null
    val loggedInUser: LiveData<User>
        get() = _loggedInUser!!

    private var _toDos: LiveData<List<ToDo>>? = null
    val toDos: LiveData<List<ToDo>>
        get() = _toDos!!

    init {
        initValues()
    }

    private fun initValues() {
        _toDos = toDoRepository.getAllToDos()
        _loggedInUser = loggedInUserRepository.getLoggedInUser()
    }

    fun logOut() {
        viewModelScope.launch(Dispatchers.IO) {
            if(loggedInUser.value != null)
                loggedInUserRepository.userLoggedOut(LoggedInUser(loggedInUser.value!!.id))
        }
    }

    fun createToDo(toDo: ToDo) {
        viewModelScope.launch(Dispatchers.IO) {
            toDoRepository.createToDo(toDo)
        }
    }

    fun updateToDo(toDo: ToDo) {
        viewModelScope.launch(Dispatchers.IO) {
            toDoRepository.updateToDo(toDo)
        }
    }

    fun deleteToDo(toDo: ToDo) {
        viewModelScope.launch(Dispatchers.IO) {
            toDoRepository.deleteToDo(toDo)
        }
    }
}