package com.rzamau.storyapp.datasource.network.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StoryDto(
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("description")
    val description: String,
    @SerialName("id")
    val id: String,
    @SerialName("lat")
    val lat: Double?,
    @SerialName("lon")
    val lon: Double?,
    @SerialName("name")
    val name: String,
    @SerialName("photoUrl")
    val photoUrl: String
)