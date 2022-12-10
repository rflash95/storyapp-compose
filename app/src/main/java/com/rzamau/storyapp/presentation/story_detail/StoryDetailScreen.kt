package com.rzamau.storyapp.presentation.story_detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mxalbert.sharedelements.SharedElement
import com.rzamau.storyapp.R
import com.rzamau.storyapp.domain.model.dummyStory
import com.rzamau.storyapp.presentation.component.TopBar
import com.rzamau.storyapp.presentation.story_detail.component.StoryDetailCard
import com.rzamau.storyapp.presentation.story_detail.component.StoryDetailImage
import com.rzamau.storyapp.presentation.theme.StoryAppTheme

@Composable
fun StoryDetailScreen(
    state: StoryDetailState,
) {

    StoryAppTheme {
        Scaffold(topBar = { TopBar(title = stringResource(R.string.story)) }) { paddingValues ->
            SharedElement(
                key = "listToDetail",
                screenKey = "detail",
                isFullscreen = true
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (state.story == null && state.isLoading) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Spacer(modifier = Modifier.padding(2.dp))
                            Text(
                                stringResource(R.string.loading),
                                style = MaterialTheme.typography.caption
                            )
                        }
                    } else if (state.story == null) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Spacer(modifier = Modifier.padding(2.dp))
                            Text(
                                stringResource(R.string.story_not_found),
                                style = MaterialTheme.typography.body1
                            )
                        }
                    } else {

                        Box {
                            StoryDetailImage(
                                url = state.story.photoUrl,
                                contentDescription = state.story.description
                            )

                            StoryDetailCard(
                                story = state.story,
                                modifier = Modifier.align(Alignment.BottomEnd)
                            )
                        }

                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun StoryDetailScreenPreview() {
    StoryDetailScreen(
        state = StoryDetailState(story = dummyStory[0])
    )
}