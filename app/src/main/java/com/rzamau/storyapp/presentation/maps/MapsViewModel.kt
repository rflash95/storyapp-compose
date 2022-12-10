package com.rzamau.storyapp.presentation.maps

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rzamau.storyapp.datasource.network.STORY_PAGINATION_PAGE_SIZE
import com.rzamau.storyapp.interactors.story_list.GetStories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MapsViewModel
@Inject
constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getStories: GetStories,
) : ViewModel() {

    val state: MutableState<MapsState> = mutableStateOf(value = MapsState())

    fun onTriggerEvent(event: MapsEvents) {
        when (event) {
            MapsEvents.LoadStories -> {
                loadStories()
            }
        }
    }


    private fun loadStories() {
        getStories.execute(
            page = 1,
            size = STORY_PAGINATION_PAGE_SIZE,
            hasLocation = true
        ).onEach { dataState ->
            state.value = state.value.copy(isLoading = dataState.isLoading)

            dataState.data?.let { stories ->
                state.value = state.value.copy(stories = stories)
            }

            dataState.message?.let { message ->
                state.value = state.value.copy(message = message)
            }
        }.launchIn(viewModelScope)
    }

}