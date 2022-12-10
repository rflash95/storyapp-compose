package com.rzamau.storyapp.presentation.story_upload.component

import android.Manifest.permission.*
import android.location.Location
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.rzamau.storyapp.R
import com.rzamau.storyapp.presentation.component.DescriptionTextField
import com.rzamau.storyapp.presentation.component.TopBar

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun StoryUploadView(
    isLoading: Boolean,
    location: Location?,
    onClickCamera: (Boolean) -> Unit,
    onClickGallery: (Boolean) -> Unit,
    previewImage: String,
    onClickUploadStoryBtn: (String) -> Unit,
    onClickMyLocation: () -> Unit,
    onBack: () -> Unit
) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val descriptionText = remember { mutableStateOf("") }
    val checkedState = remember { mutableStateOf(false) }


    val cameraPermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        listOf(
            CAMERA,
            READ_EXTERNAL_STORAGE,
            ACCESS_MEDIA_LOCATION
        )
    } else {
        listOf(
            CAMERA,
            READ_EXTERNAL_STORAGE,
            WRITE_EXTERNAL_STORAGE,
        )
    }

    val storagePermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        listOf(
            READ_EXTERNAL_STORAGE,
            ACCESS_MEDIA_LOCATION
        )
    } else {
        listOf(
            READ_EXTERNAL_STORAGE,
            WRITE_EXTERNAL_STORAGE,
        )
    }

    val cameraPermissionState = rememberMultiplePermissionsState(
        cameraPermissions
    )
    val externalStoragePermissionState = rememberMultiplePermissionsState(
        storagePermissions
    )

    val mapPermission =
        listOf(
            ACCESS_COARSE_LOCATION,
            ACCESS_FINE_LOCATION,
        )


    val mapPermissionState = rememberMultiplePermissionsState(
        mapPermission
    )

    LaunchedEffect(checkedState.value) {
        if (checkedState.value) {
            mapPermissionState.launchMultiplePermissionRequest()
            if (mapPermissionState.allPermissionsGranted) {
                onClickMyLocation()
            } else {
                checkedState.value = false
                Toast.makeText(
                    context,
                    "Location permission required for this feature to be available. Please grant the permission",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    Scaffold(topBar = {
        TopBar(
            title = stringResource(R.string.upload_new_story),
            upAvailable = true,
            onBack = onBack
        )
    }) { paddingValue ->
        if (isLoading) {
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
            Column(
                modifier = Modifier
                    .padding(paddingValue)
                    .padding(horizontal = 16.dp)
                    .fillMaxSize()
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Spacer(modifier = Modifier.padding(4.dp))
                Image(
                    painter = rememberAsyncImagePainter(
                        model = previewImage,
                        placeholder = painterResource(R.drawable.placeholder_portrait),
                        error = painterResource(R.drawable.placeholder_portrait)
                    ),
                    contentDescription = stringResource(R.string.placeholder_story),
                    contentScale = ContentScale.Fit,            // crop the image if it's not a square
                    modifier = Modifier
                        .height(350.dp)
                        .border(2.dp, Color.Cyan)   // add a border (optional)
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Row {
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .clip(MaterialTheme.shapes.large),
                        shape = RoundedCornerShape(100),
                        onClick = {

                            if (cameraPermissionState.allPermissionsGranted) {
                                onClickCamera(true)

                            } else {
                                onClickCamera(false)
                                if (externalStoragePermissionState.shouldShowRationale) {
                                    Toast.makeText(
                                        context,
                                        "Camera & Storage permission required for this feature to be available. Please grant the permission",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Camera & Storage Storage permission required for this feature to be available. Please grant the permission",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                                cameraPermissionState.launchMultiplePermissionRequest()
                            }
                        }) {
                        Text(text = stringResource(R.string.camera))
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    Button(
                        modifier = Modifier
                            .weight(1f)
                            .clip(MaterialTheme.shapes.large),
                        shape = RoundedCornerShape(100),
                        onClick = {
                            if (externalStoragePermissionState.allPermissionsGranted) {
                                onClickGallery(true)
                            } else {
                                onClickGallery(false)
                                if (externalStoragePermissionState.shouldShowRationale) {
                                    Toast.makeText(
                                        context,
                                        "Storage permission required for this feature to be available. Please grant the permission",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Storage permission required for this feature to be available. Please grant the permission",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                                externalStoragePermissionState.launchMultiplePermissionRequest()
                            }
                        }) {
                        Text(text = stringResource(R.string.gallery))

                    }
                }
                Spacer(modifier = Modifier.padding(8.dp))
                DescriptionTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    value = descriptionText.value,
                    onValueChange = { value ->
                        descriptionText.value = value
                    },
                    singleLine = false,
                    maxLines = 5,
                    placeholder = {
                        Text(text = stringResource(id = R.string.description))
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    )
                )
                Spacer(modifier = Modifier.padding(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = checkedState.value,
                        onCheckedChange = { checkedState.value = it }
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(text = "Add My Location")
                }
                Spacer(modifier = Modifier.padding(8.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(100),
                    onClick = {
                        onClickUploadStoryBtn(descriptionText.value)
                    }) {
                    Text(text = stringResource(R.string.upload))

                }
                Spacer(modifier = Modifier.padding(8.dp))

            }
        }
    }

}