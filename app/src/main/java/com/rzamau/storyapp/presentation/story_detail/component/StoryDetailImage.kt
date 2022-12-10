package com.rzamau.storyapp.presentation.story_detail.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter


@Composable
fun StoryDetailImage(
    url: String,
    contentDescription: String
) {
    val painter = rememberAsyncImagePainter(model = url)
    Box {

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            painter = painter,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop
        )

        when (painter.state) {
            is AsyncImagePainter.State.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                ) {

                }
            }
            is AsyncImagePainter.State.Success -> {

            }
            is AsyncImagePainter.State.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                ) {

                }
            }
            is AsyncImagePainter.State.Empty -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                ) {

                }
            }

        }
    }

}