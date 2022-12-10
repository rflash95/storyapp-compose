package com.rzamau.storyapp.presentation.maps

import android.Manifest
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.rzamau.storyapp.R
import com.rzamau.storyapp.presentation.component.TopBar
import com.rzamau.storyapp.presentation.theme.StoryAppTheme

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapsScreen(
    state: MapsState,
    onTriggerEvent: (MapsEvents) -> Unit,
    bottomBar: @Composable () -> Unit,

    ) {
    val context = LocalContext.current

    LaunchedEffect(state.message) {
        if (state.message.isNotEmpty()) {
            Toast.makeText(
                context,
                state.message,
                Toast.LENGTH_SHORT
            ).show()
        }
    }
    val mapPermission =
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )

    val mapPermissionState = rememberMultiplePermissionsState(
        mapPermission
    )
    LaunchedEffect(Unit) {
        onTriggerEvent(MapsEvents.LoadStories)
        mapPermissionState.launchMultiplePermissionRequest()
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-6.200000, 106.816666), 17f)
    }

    StoryAppTheme {
        Scaffold(
            topBar = {
                TopBar(
                    title = stringResource(R.string.maps),
                )
            },
            bottomBar = bottomBar
        ) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background)
                ) {
                    GoogleMap(
                        cameraPositionState = cameraPositionState,
                        modifier = Modifier.weight(1f),
                        properties = MapProperties(isMyLocationEnabled = mapPermissionState.allPermissionsGranted),
                        uiSettings = MapUiSettings(compassEnabled = true)
                    ) {
                        state.stories.forEachIndexed { index, story ->
                            if (index == 0) {
                                cameraPositionState.position =
                                    CameraPosition.fromLatLngZoom(
                                        LatLng(story.lat!!, story.lon!!),
                                        5f
                                    )
                            }
                            MarkerInfoWindow(
                                state = rememberMarkerState(
                                    position = LatLng(
                                        story.lat!!,
                                        story.lon!!
                                    ),
                                ),
                                title = story.name,
                                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED),
                                content = {
                                    Card {
                                        Column {
                                            val painter = rememberAsyncImagePainter(
                                                ImageRequest.Builder(
                                                    LocalContext.current
                                                )
                                                    .data(data = story.photoUrl)
                                                    .allowHardware(false)
                                                    .crossfade(true)
                                                    .build(),
                                            )
                                            Image(
                                                modifier = Modifier
                                                    .width(180.dp)
                                                    .height(80.dp),
                                                painter = painter,
                                                contentDescription = "",
                                                contentScale = ContentScale.Crop
                                            )
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Icon(
                                                    imageVector = Icons.Filled.Person,
                                                    contentDescription = stringResource(R.string.name_icon),
                                                    tint = Color.Gray
                                                )
                                                Spacer(modifier = Modifier.padding(4.dp))
                                                Text(
                                                    text = story.name,
                                                    style = MaterialTheme.typography.caption
                                                )

                                            }
                                            Row {
                                                Icon(
                                                    imageVector = Icons.Filled.ChatBubble,
                                                    contentDescription = stringResource(R.string.name_icon),
                                                    tint = Color.Gray
                                                )
                                                Spacer(modifier = Modifier.padding(4.dp))
                                                Text(
                                                    text = story.description,
                                                    style = MaterialTheme.typography.caption,
                                                    maxLines = 2,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                            }
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
