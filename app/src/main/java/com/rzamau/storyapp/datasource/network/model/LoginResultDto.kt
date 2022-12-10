package com.rzamau.storyapp.datasource.network.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResultDto(
    @SerialName("name")
    val name: String,
    @SerialName("token")
    val token: String,
    @SerialName("userId")
    val userId: String
)