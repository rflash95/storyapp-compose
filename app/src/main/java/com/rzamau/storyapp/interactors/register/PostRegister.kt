package com.rzamau.storyapp.interactors.register

import com.rzamau.storyapp.datasource.network.AuthService
import com.rzamau.storyapp.datasource.network.model.ErrorResponse
import com.rzamau.storyapp.domain.util.DataState
import io.ktor.client.features.*
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json


class PostRegister(
    private val authService: AuthService,
) {
    fun execute(
        name: String,
        email: String,
        password: String,
    ): Flow<DataState<Boolean>> = flow {
        try {
            emit(DataState.loading())
            val register = authService.register(name, email, password)
            val success = !register.error
            emit(DataState.data(message = null, data = success))
        } catch (e: Exception) {
            if (e is ClientRequestException) {
                val res = e.response.readText(Charsets.UTF_8)
                try {
                    val json = Json { ignoreUnknownKeys = true }
                    val errorResponse = json
                        .decodeFromString(ErrorResponse.serializer(), res)
                    emit(DataState.error(message = errorResponse.message))
                } catch (e: Exception) {
                    emit(DataState.error(message = e.message))
                }
            } else {
                emit(DataState.error(message = e.message))
            }
        }
    }
}