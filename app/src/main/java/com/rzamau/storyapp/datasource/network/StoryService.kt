package com.rzamau.storyapp.datasource.network

import com.rzamau.storyapp.domain.model.Story
import java.io.File

interface StoryService {
    suspend fun get(
        id: String,
        token: String
    ): Story

    suspend fun stories(
        page: Int,
        size: Int,
        token: String,
        hasLocation: Boolean = false
    ): List<Story>

    suspend fun postStory(
        token: String,
        description: String,
        file: File,
        lat: Double?,
        lon: Double?
    ): Boolean

}