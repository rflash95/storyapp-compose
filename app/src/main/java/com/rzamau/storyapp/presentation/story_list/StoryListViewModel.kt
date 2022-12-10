package com.rzamau.storyapp.presentation.story_list

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rzamau.storyapp.domain.model.Story
import com.rzamau.storyapp.interactors.pref.DeleteUserPref
import com.rzamau.storyapp.interactors.story_list.GetStoriesPagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class StoryListViewModel
@Inject
constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getStoriesPagingData: GetStoriesPagingData,
    private val deleteUserPref: DeleteUserPref,
) : ViewModel() {
    val state: MutableState<StoryListState> = mutableStateOf(value = StoryListState())
    val stories: Flow<PagingData<Story>> =
        getStoriesPagingData.execute().mapNotNull { it }.cachedIn(viewModelScope)


    fun onTriggerEvent(event: StoryListEvents) {
        when (event) {
            StoryListEvents.OnLogout -> {
                logoutUser()
            }
            is StoryListEvents.OnRefresh -> {
                state.value = state.value.copy(isRefreshing = event.isRefresh)
            }
        }
    }


    private fun logoutUser() {
        deleteUserPref.execute().onEach { dataState ->
            state.value = state.value.copy(isRefreshing = dataState.isLoading)

            dataState.data?.let { isLogout ->
                state.value = state.value.copy(isLogoutSuccessfully = isLogout)
            }
        }.launchIn(viewModelScope)
    }


}