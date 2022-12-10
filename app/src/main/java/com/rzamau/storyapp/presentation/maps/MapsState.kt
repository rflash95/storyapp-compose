package com.rzamau.storyapp.presentation.maps

import com.rzamau.storyapp.domain.model.Story

data class MapsState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val page: Int = 1,
    val stories: List<Story> = listOf(),
    val message: String = ""
)