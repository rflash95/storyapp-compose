package com.rzamau.storyapp.datasource.network

import com.rzamau.storyapp.datasource.network.model.LoginResultDto
import com.rzamau.storyapp.datasource.network.model.StoryDto
import com.rzamau.storyapp.domain.model.Story
import com.rzamau.storyapp.domain.model.User
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

class KtorClientFactory {
    fun build(): HttpClient {
        return HttpClient(Android) {
            install(JsonFeature) {
                serializer = KotlinxSerializer(
                    kotlinx.serialization.json.Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
    }
}

//mapper
fun LoginResultDto.toUser(): User {
    return User(
        id = userId,
        name = name,
        token = token
    )
}

fun StoryDto.toStory(): Story {
    val date = ZonedDateTime.parse(createdAt).withZoneSameInstant(ZoneId.systemDefault())
    return Story(
        id = id,
        name = name,
        description = description,
        photoUrl = photoUrl,
        lat = lat,
        lon = lon,
        createdAt = date.toLocalDateTime().toKotlinLocalDateTime()
    )
}


fun List<StoryDto>.toStoryList(): List<Story> {
    return map { it.toStory() }
}

