package com.rzamau.storyapp.presentation.story_upload

import android.net.Uri

sealed class StoryUploadEvents {
    data class OnUploadStory(
        val description: String, val uri: Uri, val lat: Double? = null, val lon: Double? = null
    ) : StoryUploadEvents()

    object OnRequestLocation : StoryUploadEvents()
}