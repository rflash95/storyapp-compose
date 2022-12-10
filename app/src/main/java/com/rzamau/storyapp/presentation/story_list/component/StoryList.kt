package com.rzamau.storyapp.presentation.story_list.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.rzamau.storyapp.R
import com.rzamau.storyapp.domain.model.Story
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StoryList(
    paddingValues: PaddingValues,
    stories: Flow<PagingData<Story>>,
    onClickStoryListItem: (Story) -> Unit,
    forceRefresh: Boolean,
) {
    val lazyPagingItems = stories.collectAsLazyPagingItems()
    val state = rememberSwipeRefreshState(
        isRefreshing = lazyPagingItems.loadState.refresh is LoadState.Loading,
    )

    LaunchedEffect(forceRefresh) {
        if (forceRefresh) {
            delay(200)
            lazyPagingItems.refresh()
        }
    }


    val scope = rememberCoroutineScope()

    SwipeRefresh(
        state = state,
        onRefresh = { lazyPagingItems.refresh() },
    ) {


        LazyColumn(
            modifier = Modifier.padding(paddingValues),
        ) {
            items(
                items = lazyPagingItems
            ) { story ->
                story?.let {
                    StoryCard(
                        story = it,
                        onClick = {
                            onClickStoryListItem(story)
                        })
                }
            }
            lazyPagingItems.apply {
                when {
                    loadState.append is LoadState.Loading -> {
                        item { LoadingItem() }
                    }
                    loadState.refresh is LoadState.Error -> {
                        item {
                            ErrorItem(
                                message = stringResource(R.string.failed_load_data),
                                modifier = Modifier.fillParentMaxSize(),
                                onClickRetry = { retry() }
                            )
                        }
                    }
                    loadState.append is LoadState.Error -> {
                        item {
                            ErrorItem(
                                message = stringResource(R.string.failed_load_data),
                                onClickRetry = { retry() }
                            )
                        }
                    }
                }
            }

        }
    }

}



