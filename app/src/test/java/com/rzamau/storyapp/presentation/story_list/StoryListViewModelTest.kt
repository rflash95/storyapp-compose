package com.rzamau.storyapp.presentation.story_list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListUpdateCallback
import com.rzamau.storyapp.MainDispatcherRule
import com.rzamau.storyapp.StoryPagingSourceTest.Companion.snapshot
import com.rzamau.storyapp.domain.model.Story
import com.rzamau.storyapp.domain.util.DataState
import com.rzamau.storyapp.interactors.pref.DeleteUserPref
import com.rzamau.storyapp.interactors.story_list.GetStoriesPagingData
import com.rzamau.storyapp.utils.DataDummy
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StoryListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var savedStateHandle: SavedStateHandle

    @Mock
    private lateinit var getStoriesPagingData: GetStoriesPagingData

    @Mock
    private lateinit var deleteUserPref: DeleteUserPref


    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `when Get stories paging data Should Not Null and Success`() = runTest {
        val dummyStories = DataDummy.generateDummyStories()
        val data = snapshot(dummyStories)
        val expectedResponse: Flow<PagingData<Story>> = flow {
            emit(data)
        }
        `when`(getStoriesPagingData.execute()).thenReturn(expectedResponse)

        val storyListViewModel = StoryListViewModel(
            savedStateHandle = savedStateHandle,
            getStoriesPagingData = getStoriesPagingData,
            deleteUserPref = deleteUserPref
        )

        val differ = AsyncPagingDataDiffer(
            diffCallback = DIFF_CALLBACK_TEST,
            updateCallback = noopListUpdateCallback,
            mainDispatcher = mainDispatcherRule.testDispatcher,
            workerDispatcher = mainDispatcherRule.testDispatcher
        )

        val collectJob = launch(UnconfinedTestDispatcher()) {
            storyListViewModel.stories.collect {
                differ.submitData(it)
            }
        }

        verify(getStoriesPagingData).execute()
        Assert.assertNotNull(differ.snapshot())
        assertEquals(dummyStories.size, differ.snapshot().size)
        assertEquals(dummyStories, differ.snapshot())
        collectJob.cancel()
    }

    @Test
    fun `when Logout successfully`() = runTest {
        val storyListViewModel = StoryListViewModel(
            savedStateHandle = savedStateHandle,
            getStoriesPagingData = getStoriesPagingData,
            deleteUserPref = deleteUserPref
        )
        val state = storyListViewModel.state
        val expectedResponse: Flow<DataState<Boolean>> = flow {
            emit(DataState.data(data = true))
        }

        `when`(deleteUserPref.execute()).thenReturn(expectedResponse)
        storyListViewModel.onTriggerEvent(StoryListEvents.OnLogout)
        verify(deleteUserPref).execute()
        Assert.assertTrue(state.value.isLogoutSuccessfully)
    }
}

private val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}

val DIFF_CALLBACK_TEST = object : DiffUtil.ItemCallback<Story>() {
    override fun areItemsTheSame(oldItem: Story, newItem: Story): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Story, newItem: Story): Boolean {
        return oldItem.id == newItem.id
    }
}
