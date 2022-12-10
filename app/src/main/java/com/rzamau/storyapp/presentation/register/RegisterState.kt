package com.rzamau.storyapp.presentation.register

data class RegisterState(
    val isLoading: Boolean = false,
    val message: String = "",
    val isRegisterSuccessfully: Boolean = false,
    val isVisible: Boolean = false,
)