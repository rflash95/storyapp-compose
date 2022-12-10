package com.rzamau.storyapp.presentation.story_list.component

import DatetimeUtil
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.mxalbert.sharedelements.SharedElement
import com.rzamau.storyapp.R
import com.rzamau.storyapp.domain.model.Story
import com.rzamau.storyapp.domain.model.dummyStory

@Composable
fun StoryCard(
    story: Story,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val dateTimeUtil = remember { DatetimeUtil() }
    val uploadTime = remember { mutableStateOf("") }

    LaunchedEffect(uploadTime) {
        uploadTime.value = dateTimeUtil.humanizeDatetime(context, story.createdAt)
    }


    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(vertical = 6.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = 8.dp
    ) {
        Column {
            val painter = rememberAsyncImagePainter(model = story.photoUrl)
            val storyImageHeight = 260

            Box {

                SharedElement(
                    key = "listToDetail",
                    screenKey = "list"
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(storyImageHeight.dp),
                        painter = painter,
                        contentDescription = story.description,
                        contentScale = ContentScale.Crop
                    )
                }


                when (painter.state) {
                    is AsyncImagePainter.State.Error -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(storyImageHeight.dp)
                        ) {

                        }
                    }
                    is AsyncImagePainter.State.Success -> {

                    }
                    is AsyncImagePainter.State.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(storyImageHeight.dp)
                        ) {

                        }
                    }
                    is AsyncImagePainter.State.Empty -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(storyImageHeight.dp)
                        ) {

                        }
                    }

                }
            }

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
    StoryCard(story = dummyStory[0], onClick = {})
}