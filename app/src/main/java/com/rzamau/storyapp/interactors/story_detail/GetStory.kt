package com.rzamau.storyapp.interactors.story_detail

import com.rzamau.storyapp.datasource.cache.StoryDatabase
import com.rzamau.storyapp.datasource.cache.toStory
import com.rzamau.storyapp.datasource.network.model.ErrorResponse
import com.rzamau.storyapp.domain.model.Story
import com.rzamau.storyapp.domain.util.DataState
import io.ktor.client.features.*
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json


class GetStory(
    private val storyDatabase: StoryDatabase,
) {
    fun execute(
        id: String,
    ): Flow<DataState<Story>> = flow {
        try {
            emit(DataState.loading())
            val story = storyDatabase.storyDao().getStory(id).toStory()
            emit(DataState.data(message = null, data = story))
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