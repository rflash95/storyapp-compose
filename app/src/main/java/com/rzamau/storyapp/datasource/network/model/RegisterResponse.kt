package com.rzamau.storyapp.datasource.network.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    @SerialName("error")
    val error: Boolean,
    @SerialName("message")
    val message: String
)