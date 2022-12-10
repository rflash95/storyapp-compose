package com.rzamau.storyapp.presentation.welcome.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

const val WELCOME_STORY_IMAGE_HEIGHT = 360

@Composable
fun WelcomeStoryImage(
    id: Int,
    contentDescription: String
) {
    Box {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(WELCOME_STORY_IMAGE_HEIGHT.dp),
            painter = painterResource(id = id),
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop
        )


    }

}