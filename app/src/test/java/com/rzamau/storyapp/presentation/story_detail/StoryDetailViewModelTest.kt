package com.rzamau.storyapp.presentation.story_detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.rzamau.storyapp.MainDispatcherRule
import com.rzamau.storyapp.domain.model.Story
import com.rzamau.storyapp.domain.util.DataState
import com.rzamau.storyapp.interactors.story_detail.GetStory
import com.rzamau.storyapp.utils.DataDummy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class StoryDetailViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var storyDetailViewModel: StoryDetailViewModel
    private val dummyStories = DataDummy.generateDummyStories()
    private val mockStoryId = "story-1"

    @Mock
    private lateinit var savedStateHandle: SavedStateHandle

    @Mock
    private lateinit var getStory: GetStory

    @Before
    fun setup() {
        storyDetailViewModel = StoryDetailViewModel(
            savedStateHandle = savedStateHandle,
            getStory = getStory
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @ExperimentalCoroutinesApi
    @Test
    fun `when Get Story Should Not Null`() = runTest {
        val state = storyDetailViewModel.state
        val storyId = "story-1"

        val expectedStory: Flow<DataState<Story>> = flow {
            emit(DataState.data(data = dummyStories.first { it.id == storyId }))
        }

        Mockito.`when`(getStory.execute(id = mockStoryId))
            .thenReturn(expectedStory)

        storyDetailViewModel.onTriggerEvent(StoryDetailEvents.LoadStory(id = storyId))

        Mockito.verify(getStory).execute(id = mockStoryId)
        assertNotNull(state.value.story)
        assertEquals(expectedStory.first().data, state.value.story)
    }

}