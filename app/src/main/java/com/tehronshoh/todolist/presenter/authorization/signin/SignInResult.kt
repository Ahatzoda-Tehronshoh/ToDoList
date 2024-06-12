package com.tehronshoh.todolist.presenter.authorization.signin

sealed class SignInResult {
    object IsLoading: SignInResult()
    data class Error(val message: String) : SignInResult()
}