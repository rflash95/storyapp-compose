package com.rzamau.storyapp.presentation.maps

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.rzamau.storyapp.MainDispatcherRule
import com.rzamau.storyapp.domain.model.Story
import com.rzamau.storyapp.domain.util.DataState
import com.rzamau.storyapp.interactors.story_list.GetStories
import com.rzamau.storyapp.utils.DataDummy
import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MapsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mapsViewModel: MapsViewModel
    private val dummyStories = DataDummy.generateDummyStories()

    @Mock
    private lateinit var savedStateHandle: SavedStateHandle

    @Mock
    private lateinit var getStories: GetStories

    @Before
    fun setup() {
        mapsViewModel = MapsViewModel(
            savedStateHandle = savedStateHandle,
            getStories = getStories
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @ExperimentalCoroutinesApi
    @Test
    fun `when Get stories Should Not Null and Success`() = runTest {
        val state = mapsViewModel.state
        val expectedStories: Flow<DataState<List<Story>>> = flow {
            emit(DataState.data(data = dummyStories))
        }

        `when`(getStories.execute(page = 1, size = 10, hasLocation = true))
            .thenReturn(expectedStories)

        mapsViewModel.onTriggerEvent(MapsEvents.LoadStories)
        Mockito.verify(getStories).execute(page = 1, size = 10, hasLocation = true)
        assertNotNull(state.value.stories)
        assertTrue(state.value.stories.isNotEmpty())
        assertEquals(dummyStories.size, state.value.stories.size)
        assertEquals(dummyStories, state.value.stories)
    }
}
