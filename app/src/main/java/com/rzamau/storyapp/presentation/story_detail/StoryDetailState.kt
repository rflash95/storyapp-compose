package com.rzamau.storyapp.presentation.story_detail

import com.rzamau.storyapp.domain.model.Story

data class StoryDetailState(
    val isLogin: Boolean = false,
    val isLoading: Boolean = true,
    val story: Story? = null,
    val message: String = ""
)