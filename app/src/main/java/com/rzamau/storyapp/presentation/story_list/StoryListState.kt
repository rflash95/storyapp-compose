package com.rzamau.storyapp.presentation.story_list

data class StoryListState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isLogoutSuccessfully: Boolean = false,
    val message: String = ""
)