package com.tehronshoh.todolist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tehronshoh.todolist.data.ToDo
import com.tehronshoh.todolist.data.repository.ToDoRepository
import kotlinx.coroutines.launch

class MainViewModel(private val toDoRepository: ToDoRepository) : ViewModel() {

    private var _toDos: LiveData<List<ToDo>>? = null
    val toDos: LiveData<List<ToDo>>
        get() = _toDos!!

    init {
        loadToDos()
    }

    private fun loadToDos() {
        _toDos = toDoRepository.getAllToDos()
    }

    fun createToDo(toDo: ToDo) {
        viewModelScope.launch {
            toDoRepository.createToDo(toDo)
        }
    }

    fun updateToDo(toDo: ToDo) {
        viewModelScope.launch {
            toDoRepository.updateToDo(toDo)
        }
    }

    fun deleteToDo(toDo: ToDo) {
        viewModelScope.launch {
            toDoRepository.deleteToDo(toDo)
        }
    }
}