package com.rzamau.storyapp.presentation.story_list

import android.widget.Toast
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import com.rzamau.storyapp.R
import com.rzamau.storyapp.domain.model.Story
import com.rzamau.storyapp.presentation.component.TopBar
import com.rzamau.storyapp.presentation.story_list.component.StoryList
import com.rzamau.storyapp.presentation.theme.StoryAppTheme
import kotlinx.coroutines.flow.Flow

@Composable
fun StoryListScreen(
    state: StoryListState,
    bottomBar: @Composable () -> Unit,
    onTriggerEvent: (StoryListEvents) -> Unit,
    onClickStoryListItem: (Story) -> Unit,
    onClickUploadStoryBtn: () -> Unit,
    onLoginSuccessfully: () -> Unit,
    stories: Flow<PagingData<Story>>,
    isForceRefresh: Boolean?
) {
    StoryAppTheme {

        val hasAlreadyNavigated = remember { mutableStateOf(false) }

        val context = LocalContext.current
        if (!hasAlreadyNavigated.value && state.isLogoutSuccessfully) {
            onLoginSuccessfully()
            hasAlreadyNavigated.value = true
        }


        LaunchedEffect(state.message) {
            if (state.message.isNotEmpty()) {
                Toast.makeText(
                    context,
                    state.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        Scaffold(
            topBar = {
                TopBar(
                    title = stringResource(R.string.stories),
                    isLogin = true,
                    onLogout = { onTriggerEvent(StoryListEvents.OnLogout) }
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { onClickUploadStoryBtn() }) {
                    Icon(Icons.Filled.Add, stringResource(R.string.add_story))
                }
            },
            bottomBar = bottomBar
        ) { paddingValues ->

            StoryList(
                paddingValues = paddingValues,
                stories = stories,
                forceRefresh = isForceRefresh ?: false,
                onClickStoryListItem = onClickStoryListItem,
            )
        }
    }
}

@Preview
@Composable
fun StoryListScreenPreview() {

}