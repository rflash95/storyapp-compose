package com.rzamau.storyapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChatBubble
import androidx.compose.material.icons.rounded.Map
import androidx.compose.ui.graphics.vector.ImageVector
import com.rzamau.storyapp.R

sealed class Screen(
    val route: String,
    val label: Int,
    val icon: ImageVector? = null
) {
    object Welcome : Screen("welcome", R.string.welcome)
    object Login : Screen("login", R.string.login)
    object Register : Screen("register", R.string.register)
    object StoryList : Screen("storyList", R.string.stories, Icons.Rounded.ChatBubble)
    object StoryDetail : Screen("storyDetail", R.string.story)
    object StoryUpload : Screen("storyAdd", R.string.add_story)
    object Maps : Screen("maps", R.string.maps, Icons.Rounded.Map)

}