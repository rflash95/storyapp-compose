package com.rzamau.storyapp.datasource.network

import com.rzamau.storyapp.datasource.network.model.LoginResponse
import com.rzamau.storyapp.datasource.network.model.RegisterResponse
import com.rzamau.storyapp.domain.model.User
import io.ktor.client.*
import io.ktor.client.request.forms.*
import io.ktor.http.*

class AuthServiceImpl(
    private val httpClient: HttpClient,
) : AuthService {
    override suspend fun login(email: String, password: String): User {
        return httpClient.submitForm<LoginResponse>(
            url = "$BASE_URL/login",
            formParameters = Parameters.build {
                append("email", email)
                append("password", password)
            },
        ).loginResultDto.toUser()
    }

    override suspend fun register(name: String, email: String, password: String): RegisterResponse {
        return httpClient.submitForm<RegisterResponse>(
            url = "$BASE_URL/register",
            formParameters = Parameters.build {
                append("name", name)
                append("email", email)
                append("password", password)
            },
        )
    }
}
