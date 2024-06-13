package com.tehronshoh.todolist.presentation.ui.authorization.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tehronshoh.todolist.data.model.LoggedInUser
import com.tehronshoh.todolist.domain.repository.LoggedInUserRepository
import com.tehronshoh.todolist.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignInViewModel(
    private val userRepository: UserRepository,
    private val loggedInUserRepository: LoggedInUserRepository
) : ViewModel() {
    private var _signInResult: MutableLiveData<SignInResult> = MutableLiveData()
    val signInResult: LiveData<SignInResult>
        get() = _signInResult

    fun isUserWithExists(email: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val user = userRepository.isUserExist(email, password)
            if (user != null) {
                loggedInUserRepository.userLoggedIn(LoggedInUser(user.id))
            } else
                _signInResult.postValue(SignInResult.Error("User not found"))
        } catch (e: Exception) {
            _signInResult.postValue(SignInResult.Error(e.message.toString()))
        }
    }
}