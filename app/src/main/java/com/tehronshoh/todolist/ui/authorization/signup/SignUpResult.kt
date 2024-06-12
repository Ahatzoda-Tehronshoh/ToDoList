package com.tehronshoh.todolist.ui.authorization.signup

sealed class SignUpResult {
    object IsLoading: SignUpResult()
    data class Error(val emailMessage: String?, val passwordMessage: String?) : SignUpResult()
}