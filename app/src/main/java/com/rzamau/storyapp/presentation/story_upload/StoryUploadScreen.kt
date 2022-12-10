package com.rzamau.storyapp.presentation.story_upload

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.rzamau.storyapp.R
import com.rzamau.storyapp.presentation.story_upload.component.CameraCapture
import com.rzamau.storyapp.presentation.story_upload.component.GallerySelect
import com.rzamau.storyapp.presentation.story_upload.component.StoryUploadView
import com.rzamau.storyapp.presentation.theme.StoryAppTheme

@Composable
fun StoryUploadScreen(
    state: StoryUploadState,
    onTriggerEvent: (StoryUploadEvents) -> Unit,
    onBack: () -> Unit,
    navigateUp: () -> Unit
) {
    val context = LocalContext.current
    val action = remember { mutableStateOf("") }
    val previewImage = remember { mutableStateOf("") }

    LaunchedEffect(state.message) {
        if (state.message.isNotEmpty()) {
            Toast.makeText(
                context,
                state.message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    BackHandler(enabled = true) {
        when (action.value) {
            "camera" -> {
                action.value = ""
            }
            "gallery" -> {
                action.value = ""
            }
            else -> {
                navigateUp()
            }
        }
    }
    LaunchedEffect(state.isUploaded) {
        if (state.isUploaded) {
            Toast.makeText(
                context,
                context.getText(R.string.story_successfully_uploaded),
                Toast.LENGTH_LONG
            ).show()
            navigateUp()
        }
    }

    StoryAppTheme {
        if (state.isLoading) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.padding(2.dp))
                Text(stringResource(R.string.loading), style = MaterialTheme.typography.caption)
            }
        } else {
            Crossfade(
                targetState = action.value,
                animationSpec = tween(800)
            ) { actionSelected ->
                when (actionSelected) {
                    "camera" -> {
                        CameraCapture(onImageFile = { file ->
                            action.value = ""
                            previewImage.value = file.toURI().toString()
                        })
                    }
                    "gallery" -> {
                        GallerySelect(onImageUri = { uri ->
                            action.value = ""
                            previewImage.value = uri.toString()
                        })
                    }
                    else -> {
                        StoryUploadView(
                            isLoading = state.isLoading,
                            location = state.location,
                            onClickCamera = { isGranted ->
                                action.value = if (isGranted) "camera" else ""
                            },
                            onClickGallery = { isGranted ->
                                action.value = if (isGranted) "gallery" else ""
                            },
                            previewImage = previewImage.value,
                            onClickUploadStoryBtn = { descriptionText ->
                                if (previewImage.value.isNotEmpty() && descriptionText.isNotEmpty()) {
                                    onTriggerEvent(
                                        StoryUploadEvents.OnUploadStory(
                                            description = descriptionText,
                                            uri = previewImage.value.toUri(),
                                            lat = state.location?.latitude,
                                            lon = state.location?.longitude,

                                            )
                                    )
                                } else {
                                    Toast.makeText(
                                        context,
                                        context.getText(R.string.image_description_required),
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            },
                            onClickMyLocation = {
                                onTriggerEvent(StoryUploadEvents.OnRequestLocation)
                            },
                            onBack = onBack,
                        )


                        if (state.message == "Payload content length greater than maximum allowed: 1000000") {
                            Toast.makeText(
                                context,
                                stringResource(R.string.file_bellow_1mb),
                                Toast.LENGTH_LONG
                            ).show()
                        } else if (state.message.isNotEmpty()) {
                            Toast.makeText(
                                context,
                                state.message,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun StoryUploadScreenPreview() {
    StoryUploadScreen(
        onTriggerEvent = {},
        state = StoryUploadState(), navigateUp = {}, onBack = {}
    )
}