package com.rzamau.storyapp.datasource.network.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("error")
    val error: Boolean,
    @SerialName("loginResult")
    val loginResultDto: LoginResultDto,
    @SerialName("message")
    val message: String
)