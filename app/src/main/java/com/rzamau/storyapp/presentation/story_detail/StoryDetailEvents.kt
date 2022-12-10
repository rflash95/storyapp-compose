package com.rzamau.storyapp.presentation.story_detail

sealed class StoryDetailEvents {
    data class LoadStory(val id: String) : StoryDetailEvents()
}