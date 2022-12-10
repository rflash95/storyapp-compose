package com.rzamau.storyapp.presentation.maps.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter


@Composable
fun StoryMapsImage(
    url: String,
    contentDescription: String,
    storyImageHeight: Int = 120
) {
    val painter = rememberAsyncImagePainter(model = url)
    Box {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(storyImageHeight.dp),
            painter = painter,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop
        )

        when (painter.state) {
            is AsyncImagePainter.State.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(storyImageHeight.dp)
                ) {

                }
            }
            is AsyncImagePainter.State.Success -> {

            }
            is AsyncImagePainter.State.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(storyImageHeight.dp)
                ) {

                }
            }
            is AsyncImagePainter.State.Empty -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(storyImageHeight.dp)
                ) {

                }
            }

        }
    }

}