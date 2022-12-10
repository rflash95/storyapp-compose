package com.rzamau.storyapp.presentation.story_list

sealed class StoryListEvents {
    object OnLogout : StoryListEvents()
    data class OnRefresh(val isRefresh: Boolean) : StoryListEvents()
}