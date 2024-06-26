package com.tehronshoh.todolist.presentation.ui.authorization.signin

sealed class SignInResult {
    object IsLoading: SignInResult()
    data class Error(val message: String) : SignInResult()
}