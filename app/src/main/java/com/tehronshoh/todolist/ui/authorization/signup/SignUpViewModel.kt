package com.tehronshoh.todolist.ui.authorization.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tehronshoh.todolist.data.model.LoggedInUser
import com.tehronshoh.todolist.data.model.User
import com.tehronshoh.todolist.data.repository.LoggedInUserRepository
import com.tehronshoh.todolist.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val userRepository: UserRepository,
    private val loggedInUserRepository: LoggedInUserRepository
) : ViewModel() {
    private var _signUpResult: MutableLiveData<SignUpResult> = MutableLiveData()
    val signUpResult: LiveData<SignUpResult>
        get() = _signUpResult

    private suspend fun isUserWithEmailExists(email: String): String? {
        val user = userRepository.isUserWithEmailExists(email)
        return if (user != null)
            "Email already exists"
        else
            null
    }

    private fun checkPassword(password: String): String? =
        if (password.length < 5)
            "Password must be at least 5 characters"
        else
            null


    fun createUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        _signUpResult.postValue(SignUpResult.IsLoading)
        val emailError = isUserWithEmailExists(user.email)
        val passwordError = checkPassword(user.password)

        if (emailError == null && passwordError == null) {
            try {
                val userId = userRepository.createUser(user)
                if(userId != -1L)
                    loggedInUserRepository.userLoggedIn(LoggedInUser(userId))
            } catch (e: Exception) {
                _signUpResult.postValue(SignUpResult.Error("", e.message.toString()))
            }
        } else
            _signUpResult.postValue(SignUpResult.Error(emailError, passwordError))
    }
}
