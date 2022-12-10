package com.rzamau.storyapp.presentation.welcome

import com.rzamau.storyapp.domain.model.User

data class WelcomeState(
    val isLogin: Boolean = false,
    var isLoading: Boolean = true,
    var user: User? = null
)