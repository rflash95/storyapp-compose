package com.rzamau.storyapp.datasource.network

import com.rzamau.storyapp.datasource.network.model.RegisterResponse
import com.rzamau.storyapp.domain.model.User

interface AuthService {
    suspend fun login(
        email: String,
        password: String
    ): User

    suspend fun register(
        name: String,
        email: String,
        password: String
    ): RegisterResponse


}