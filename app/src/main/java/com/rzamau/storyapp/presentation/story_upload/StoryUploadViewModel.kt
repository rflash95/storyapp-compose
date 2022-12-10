package com.rzamau.storyapp.presentation.story_upload

import android.location.Location
import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rzamau.storyapp.domain.util.DataState
import com.rzamau.storyapp.interactors.story_upload.PostStory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class StoryUploadViewModel
@Inject
constructor(
    private val savedStateHandle: SavedStateHandle,
    private val postStory: PostStory,
    private val locationListener: LocationListener
) : ViewModel() {

    val state: MutableState<StoryUploadState> = mutableStateOf(value = StoryUploadState())
    private val locationObserver = Observer<Location?> { location ->
        state.value = state.value.copy(location = location)
    }

    fun onTriggerEvent(event: StoryUploadEvents) {
        when (event) {
            is StoryUploadEvents.OnUploadStory -> {
                uploadStory(
                    description = event.description, uri = event.uri,
                    lat = event.lat,
                    lon = event.lon
                ).launchIn(viewModelScope)
            }
            StoryUploadEvents.OnRequestLocation -> {
                getLocation()
            }

        }
    }

    fun uploadStory(
        description: String,
        uri: Uri,
        lat: Double? = null,
        lon: Double? = null
    ): Flow<DataState<Boolean>> {
        return postStory.execute(description = description, uri = uri, lat = lat, lon = lon)
            .onEach { dataState ->
                state.value = state.value.copy(isLoading = dataState.isLoading)

                dataState.data?.let { isUploaded ->
                    state.value = state.value.copy(isUploaded = isUploaded)
                }

                dataState.message?.let { message ->
                    state.value = state.value.copy(message = message)
                    delay(200)
                    state.value = state.value.copy(message = "")
                }

            }
    }

    private fun getLocation() {
        locationListener.let {
            it.startService()
            it.observeForever(locationObserver)
        }
    }

    override fun onCleared() {
        super.onCleared()
        locationListener.removeObserver(locationObserver)
    }
}