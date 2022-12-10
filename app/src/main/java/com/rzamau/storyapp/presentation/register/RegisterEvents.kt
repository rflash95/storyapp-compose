package com.rzamau.storyapp.presentation.register

sealed class RegisterEvents {
    data class OnPostRegister(
        val name: String,
        val email: String,
        val password: String,
        val confirmPassword: String
    ) : RegisterEvents()


}