package com.rzamau.storyapp.presentation.story_upload

import android.location.Location

data class StoryUploadState(
    val isLoading: Boolean = false,
    val isUploaded: Boolean = false,
    val message: String = "",
    val location: Location? = null
)
