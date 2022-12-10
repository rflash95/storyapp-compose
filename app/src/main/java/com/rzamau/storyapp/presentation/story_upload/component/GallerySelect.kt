package com.rzamau.storyapp.presentation.story_upload.component

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect

val EMPTY_IMAGE_URI: Uri = Uri.parse("file://dev/null")

@Composable
fun GallerySelect(
    onImageUri: (Uri) -> Unit = { }
) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            onImageUri(uri ?: EMPTY_IMAGE_URI)
        }
    )

    @Composable
    fun LaunchGallery() {
        SideEffect {
            launcher.launch("image/*")
        }
    }
    LaunchGallery()
}

