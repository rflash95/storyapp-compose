package com.rzamau.storyapp.presentation.story_detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rzamau.storyapp.domain.model.Story
import com.rzamau.storyapp.domain.util.DataState
import com.rzamau.storyapp.interactors.story_detail.GetStory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class StoryDetailViewModel
@Inject
constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getStory: GetStory
) : ViewModel() {

    val state: MutableState<StoryDetailState> = mutableStateOf(value = StoryDetailState())

    init {
        savedStateHandle.get<String>("storyId")?.let { storyId ->
            onTriggerEvent(StoryDetailEvents.LoadStory(id = storyId))
        }
    }

    fun onTriggerEvent(event: StoryDetailEvents) {
        when (event) {
            is StoryDetailEvents.LoadStory -> {
                loadStory(event.id).launchIn(viewModelScope)
            }

        }
    }

    fun loadStory(id: String): Flow<DataState<Story>> {
        return getStory.execute(id).onEach { dataState ->
            state.value = state.value.copy(isLoading = dataState.isLoading)

            dataState.data?.let { story ->
                state.value = state.value.copy(story = story)
            }

            dataState.message?.let { message ->
                state.value = state.value.copy(message = message)
            }
        }
    }

}