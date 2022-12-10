package com.rzamau.storyapp.datasource.network.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StoriesResponse(
    @SerialName("error")
    val error: Boolean,
    @SerialName("listStory")
    val listStoryDto: List<StoryDto>,
    @SerialName("message")
    val message: String
)