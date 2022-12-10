package com.rzamau.storyapp.presentation.login

data class LoginState(
    val isLoading: Boolean = false,
    val message: String = "",
    val isLoginSuccessfully: Boolean = false,
    val isVisible: Boolean = true,
)