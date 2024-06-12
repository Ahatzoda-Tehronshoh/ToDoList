package com.tehronshoh.todolist.ui.add

import androidx.lifecycle.ViewModel
import com.tehronshoh.todolist.data.repository.UserRepository

class EditViewModel(private val userRepository: UserRepository): ViewModel() {
    fun getAllUsersEmail() = userRepository.getAllUsersEmail()
}