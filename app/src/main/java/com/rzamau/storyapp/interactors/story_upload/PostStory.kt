package com.rzamau.storyapp.interactors.story_upload

import android.content.Context
import android.net.Uri
import com.rzamau.storyapp.datasource.network.StoryService
import com.rzamau.storyapp.datasource.network.model.ErrorResponse
import com.rzamau.storyapp.datasource.preference.UserPreferenceService
import com.rzamau.storyapp.domain.util.DataState
import com.rzamau.storyapp.reduceFileImage
import com.rzamau.storyapp.uriToFile
import io.ktor.client.features.*
import io.ktor.client.statement.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import java.io.File


class PostStory(
    private val storyService: StoryService,
    private val context: Context,
    private val userPreferenceService: UserPreferenceService
) {
    fun execute(
        description: String,
        uri: Uri,
        lat: Double? = null,
        lon: Double? = null,
    ): Flow<DataState<Boolean>> = flow {
        try {
            emit(DataState.loading())
            val userPref = userPreferenceService.getUserFromPreferencesStore()

            val upload = storyService.postStory(
                token = userPref.first().token,
                description = description,
                file = compressUriFile(uri, context),
                lat = lat,
                lon = lon
            )
            emit(DataState.data(message = null, data = upload))
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

suspend fun compressUriFile(uri: Uri, context: Context): File = withContext(Dispatchers.Default) {
    val file = uriToFile(uri, context)
    return@withContext reduceFileImage(file)
}
