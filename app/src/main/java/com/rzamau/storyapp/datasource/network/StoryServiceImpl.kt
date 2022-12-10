package com.rzamau.storyapp.datasource.network

import com.rzamau.storyapp.datasource.network.model.StoriesResponse
import com.rzamau.storyapp.datasource.network.model.StoryDetailResponse
import com.rzamau.storyapp.domain.model.Story
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import java.io.File

class StoryServiceImpl(
    private val httpClient: HttpClient,
) : StoryService {
    override suspend fun get(id: String, token: String): Story {
        return httpClient.get<StoryDetailResponse> {
            url("$BASE_URL/stories/$id")
            header("Authorization", "Bearer $token")
        }.story.toStory()
    }

    override suspend fun stories(
        page: Int,
        size: Int,
        token: String,
        hasLocation: Boolean
    ): List<Story> {
        return httpClient.get<StoriesResponse> {
            url("$BASE_URL/stories")
            parameter("page", page)
            parameter("size", size)
            if (hasLocation) {
                parameter("location", 1)
            }
            header("Authorization", "Bearer $token")
        }.listStoryDto.toStoryList()
    }

    override suspend fun postStory(
        token: String,
        description: String,
        file: File,
        lat: Double?,
        lon: Double?
    ): Boolean {
        val httpResponse = httpClient.post<HttpResponse> {
            url("$BASE_URL/stories")
            header("Authorization", "Bearer $token")
            body = MultiPartFormDataContent(formData {
                append("description", description)
                append("photo", file.readBytes(), Headers.build {
                    append(HttpHeaders.ContentDisposition, "filename=${file.name}")
                })
                if (lat != null) {
                    append("lat", lat)
                }
                if (lon != null) {
                    append("lon", lon)
                }
            })
        }
        return httpResponse.status.value == 201
    }

}
