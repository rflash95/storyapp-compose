package com.rzamau.storyapp.interactors.story_list

import com.rzamau.storyapp.datasource.cache.StoryDatabase
import com.rzamau.storyapp.datasource.cache.toStoryEntityList
import com.rzamau.storyapp.datasource.cache.toStoryList
import com.rzamau.storyapp.datasource.network.StoryService
import com.rzamau.storyapp.datasource.network.model.ErrorResponse
import com.rzamau.storyapp.datasource.preference.UserPreferenceService
import com.rzamau.storyapp.domain.model.Story
import com.rzamau.storyapp.domain.util.DataState
import io.ktor.client.features.*
import io.ktor.client.statement.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json


class GetStories(
    private val storyService: StoryService,
    private val storyDatabase: StoryDatabase,
    private val userPreferenceService: UserPreferenceService
) {
    fun execute(
        page: Int,
        size: Int,
        hasLocation: Boolean = false
    ): Flow<DataState<List<Story>>> = flow {
        try {
            emit(DataState.loading())
            val userPref = userPreferenceService.getUserFromPreferencesStore()
            val stories =
                storyService.stories(
                    page = page,
                    size = size,
                    token = userPref.first().token,
                    hasLocation = hasLocation
                )
            storyDatabase.storyDao().insertStory(stories.toStoryEntityList())
            val result = storyDatabase.storyDao().getAllOnlyHasLocation().toStoryList()

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