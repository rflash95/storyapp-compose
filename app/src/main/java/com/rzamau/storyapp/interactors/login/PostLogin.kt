package com.rzamau.storyapp.interactors.login

import com.rzamau.storyapp.datasource.network.AuthService
import com.rzamau.storyapp.datasource.network.model.ErrorResponse
import com.rzamau.storyapp.datasource.preference.UserPreferenceService
import com.rzamau.storyapp.domain.model.User
import com.rzamau.storyapp.domain.util.DataState
import io.ktor.client.features.*
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json


class PostLogin(
    private val authService: AuthService,
    private val userPreferenceService: UserPreferenceService
) {
    fun execute(
        email: String,
        password: String,
    ): Flow<DataState<User>> = flow {
        try {
            emit(DataState.loading())
            val user = authService.login(email, password)
            userPreferenceService.saveUserToPreferencesStore(user)
            val result = userPreferenceService.getUserFromPreferencesStore().first()
            emit(DataState.data(message = null, data = result))
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