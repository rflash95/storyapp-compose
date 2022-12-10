package com.rzamau.storyapp.presentation.login

sealed class LoginEvents {
    data class OnPostLogin(val email: String, val password: String) : LoginEvents()
}