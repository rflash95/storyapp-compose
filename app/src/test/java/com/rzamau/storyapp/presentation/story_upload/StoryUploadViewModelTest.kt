package com.rzamau.storyapp.presentation.story_upload

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.rzamau.storyapp.MainDispatcherRule
import com.rzamau.storyapp.domain.util.DataState
import com.rzamau.storyapp.interactors.story_upload.PostStory
import junit.framework.Assert.assertTrue
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
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class StoryUploadViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var storyUploadViewModel: StoryUploadViewModel

    @Mock
    private lateinit var savedStateHandle: SavedStateHandle

    @Mock
    private lateinit var postStory: PostStory

    @Mock
    private lateinit var locationListener: LocationListener

    private val descriptionMock = "Haii test description"
    private val uriMock = mock(Uri::class.java)
    private val latitudeMock = -6.857053
    private val longitudeMock = 107.53229


    @Before
    fun setup() {
        storyUploadViewModel = StoryUploadViewModel(
            savedStateHandle = savedStateHandle,
            postStory = postStory,
            locationListener = locationListener
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when upload success`() = runTest {
        val state = storyUploadViewModel.state
        val expectedResponse: Flow<DataState<Boolean>> = flow {
            emit(DataState.data(data = true))
        }

        Mockito.`when`(
            postStory.execute(
                description = descriptionMock,
                uri = uriMock,
                lat = latitudeMock,
                lon = longitudeMock
            )
        ).thenReturn(expectedResponse)

        storyUploadViewModel.onTriggerEvent(
            StoryUploadEvents.OnUploadStory(
                description = descriptionMock,
                uri = uriMock,
                lat = latitudeMock,
                lon = longitudeMock
            )
        )

        Mockito.verify(postStory).execute(
            description = descriptionMock,
            uri = uriMock,
            lat = latitudeMock,
            lon = longitudeMock
        )
        assertTrue(state.value.isUploaded)
    }
}

