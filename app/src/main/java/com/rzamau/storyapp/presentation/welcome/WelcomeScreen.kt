package com.rzamau.storyapp.presentation.welcome

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rzamau.storyapp.R
import com.rzamau.storyapp.domain.model.listWelcomeStory
import com.rzamau.storyapp.presentation.component.TopBar
import com.rzamau.storyapp.presentation.theme.StoryAppTheme
import com.rzamau.storyapp.presentation.welcome.component.HorizontalPagerWithOffsetTransition
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun WelcomeScreen(
    state: WelcomeState,
    onIsLogin: () -> Unit,
    onClickLoginBtn: () -> Unit,
    onClickRegisterBtn: () -> Unit,
) {
    val hasAlreadyNavigated = remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    if (!hasAlreadyNavigated.value && state.isLogin && !state.isLoading) {
        LaunchedEffect(Unit) {
            scope.launch {
                delay(1000)
                onIsLogin()
                hasAlreadyNavigated.value = true
            }
        }
    }

    StoryAppTheme {
        Scaffold(topBar = { TopBar(title = stringResource(R.string.welcome)) }) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = stringResource(R.string.welcome_story_app),
                    style = MaterialTheme.typography.h5,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = stringResource(R.string.welcome_description),
                    style = MaterialTheme.typography.caption,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.padding(8.dp))
                HorizontalPagerWithOffsetTransition(stories = listWelcomeStory)
                Spacer(modifier = Modifier.padding(8.dp))
                Box(modifier = Modifier.height(140.dp)) {
                    this@Column.AnimatedVisibility(
                        visible = true,
                        enter = fadeIn(tween(500))
                    ) {
                        Row(horizontalArrangement = Arrangement.Center) {
                            Crossfade(
                                targetState = state.isLoading,
                                animationSpec = tween(500)
                            ) { isLoading ->
                                if (isLoading && !state.isLogin) {
                                    Column(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        CircularProgressIndicator()
                                    }
                                } else if (state.isLogin) {
                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            stringResource(
                                                R.string.welcome_back,
                                                state.user?.name ?: ""
                                            )
                                        )
                                        //${state.user?.name}
                                    }
                                } else if (!state.isLogin) {
                                    Row(horizontalArrangement = Arrangement.Center) {
                                        Button(modifier = Modifier.weight(1f),
                                            onClick = { onClickRegisterBtn() }) {
                                            Text(text = stringResource(R.string.register))
                                        }
                                        Spacer(modifier = Modifier.padding(8.dp))
                                        Button(modifier = Modifier.weight(1f),
                                            onClick = { onClickLoginBtn() }) {
                                            Text(text = stringResource(R.string.login))
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(onClickLoginBtn = {}, onClickRegisterBtn = {},
        state = WelcomeState(isLogin = false),
        onIsLogin = {})
}