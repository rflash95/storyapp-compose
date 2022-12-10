package com.rzamau.storyapp.presentation.story_detail.component

import DatetimeUtil
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rzamau.storyapp.R
import com.rzamau.storyapp.domain.model.Story
import com.rzamau.storyapp.domain.model.dummyStory

@Composable
fun StoryDetailCard(
    story: Story,
    modifier: Modifier,
) {
    val context = LocalContext.current
    val dateTimeUtil = remember { DatetimeUtil() }
    val uploadTime = remember { mutableStateOf("") }

    LaunchedEffect(uploadTime) {
        uploadTime.value = dateTimeUtil.humanizeDatetime(context, story.createdAt)
    }


    Card(
        shape = MaterialTheme.shapes.small,
        modifier = modifier
            .fillMaxWidth(),
        elevation = 8.dp
    ) {
        Column {
            Column(
                modifier = Modifier.padding(8.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Filled.ChatBubble,
                        contentDescription = stringResource(R.string.name_icon),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        text = story.description,
                        maxLines = 5,
                        overflow = TextOverflow.Ellipsis
                    )

                }
                Spacer(modifier = Modifier.padding(4.dp))
                Divider()
                Spacer(modifier = Modifier.padding(4.dp))
                Row {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = stringResource(R.string.name_icon),
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(text = story.name)

                    }
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CalendarMonth,
                            contentDescription = stringResource(R.string.name_icon),
                            tint = Color.Gray
                        )
                        Spacer(modifier = Modifier.padding(4.dp))
                        Text(text = uploadTime.value)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun StoryCardPreview() {
    StoryDetailCard(story = dummyStory[0], modifier = Modifier)
}