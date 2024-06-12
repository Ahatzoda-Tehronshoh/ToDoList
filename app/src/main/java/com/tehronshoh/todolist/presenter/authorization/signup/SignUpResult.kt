package com.tehronshoh.todolist.presenter.authorization.signup

sealed class SignUpResult {
    object IsLoading: SignUpResult()
    data class Error(val emailMessage: String?, val passwordMessage: String?) : SignUpResult()
}