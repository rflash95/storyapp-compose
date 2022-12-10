package com.rzamau.storyapp.presentation.login

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rzamau.storyapp.R
import com.rzamau.storyapp.presentation.component.EmailTextField
import com.rzamau.storyapp.presentation.component.PasswordTextField
import com.rzamau.storyapp.presentation.component.TopBar
import com.rzamau.storyapp.presentation.theme.StoryAppTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    state: LoginState,
    onLoginSuccessfully: () -> Unit,
    onTriggerEvent: (LoginEvents) -> Unit,
    onBack: () -> Unit,
    isVisible: Boolean = false
) {
    StoryAppTheme {
        val emailText = rememberSaveable { mutableStateOf("") }
        val passwordText = rememberSaveable { mutableStateOf("") }

        val isVisibleField = rememberSaveable { mutableStateOf(isVisible) }


        val hasAlreadyNavigated = remember { mutableStateOf(false) }

        val keyboardController = LocalSoftwareKeyboardController.current


        LaunchedEffect(Unit) {
            isVisibleField.value = true
        }

        LaunchedEffect(state.isLoginSuccessfully) {
            Log.d("TAG", "LoginScreen: ${state.isLoginSuccessfully}")
            if (!hasAlreadyNavigated.value && state.isLoginSuccessfully) {
                onLoginSuccessfully()
                hasAlreadyNavigated.value = true
            }
        }


        Scaffold(topBar = {
            TopBar(
                title = stringResource(R.string.login),
                upAvailable = true,
                onBack = onBack
            )
        }) { paddingValue ->
            Crossfade(
                targetState = state.isLoading,
                animationSpec = tween(500)
            ) { isLoading ->
                if (isLoading) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                        Text(
                            stringResource(R.string.please_wait),
                            style = MaterialTheme.typography.caption
                        )
                    }

                } else {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .padding(paddingValue)
                            .padding(horizontal = 16.dp)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Box(modifier = Modifier.height(140.dp)) {
                            this@Column.AnimatedVisibility(
                                visible = isVisibleField.value,
                                enter = fadeIn(tween(1500))
                            ) {
                                Image(
                                    painter = painterResource(R.drawable.avatar),
                                    contentDescription = "avatar",
                                    contentScale = ContentScale.Crop,            // crop the image if it's not a square
                                    modifier = Modifier
                                        .size(140.dp)
                                        .clip(CircleShape)                       // clip to the circle shape
                                        .border(
                                            2.dp,
                                            Color.Cyan,
                                            CircleShape
                                        )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.padding(8.dp))
                        Crossfade(
                            targetState = state.message,
                            animationSpec = tween(2000)
                        ) { message ->
                            if (message == "Invalid password") {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(Color.Red.copy(alpha = 0.8f))
                                ) {
                                    Text(
                                        modifier = Modifier.padding(8.dp),
                                        text = stringResource(id = R.string.password_incorrect),
                                        style = MaterialTheme.typography.caption,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                        Box(modifier = Modifier.height(80.dp)) {
                            this@Column.AnimatedVisibility(
                                visible = isVisibleField.value,
                                enter = fadeIn(tween(3000))
                            ) {
                                EmailTextField(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    value = emailText.value,
                                    onValueChange = { value ->
                                        emailText.value = value
                                    },
                                    label = {
                                        Text(text = stringResource(R.string.email))
                                    },
                                    singleLine = true,
                                    leadingIcon = {
                                        IconButton(onClick = {}) {
                                            Icon(
                                                imageVector = Icons.Filled.Email,
                                                contentDescription = "Email Icon"
                                            )
                                        }
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Email,
                                        imeAction = ImeAction.Next
                                    )
                                )
                            }
                        }
                        Spacer(modifier = Modifier.padding(2.dp))
                        Box(modifier = Modifier.height(80.dp)) {
                            this@Column.AnimatedVisibility(
                                visible = isVisibleField.value,
                                enter = fadeIn(tween(3500))
                            ) {
                                PasswordTextField(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    value = passwordText.value,
                                    onValueChange = { value ->
                                        passwordText.value = value
                                    },
                                    label = {
                                        Text(text = stringResource(R.string.password))
                                    },
                                    singleLine = true,
                                    leadingIcon = {
                                        IconButton(onClick = {}) {
                                            Icon(
                                                imageVector = Icons.Filled.Lock,
                                                contentDescription = "Password Icon"
                                            )
                                        }
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Password,
                                        imeAction = ImeAction.Done
                                    ),
                                    keyboardActions = KeyboardActions(
                                        onDone = { keyboardController?.hide() }
                                    ),
                                    visualTransformation = PasswordVisualTransformation()
                                )

                            }
                        }
                        Box(modifier = Modifier.height(140.dp)) {
                            this@Column.AnimatedVisibility(
                                visible = isVisibleField.value,
                                enter = fadeIn(tween(4500))
                            ) {
                                Button(modifier = Modifier.fillMaxWidth(), onClick = {
                                    onTriggerEvent(
                                        LoginEvents.OnPostLogin(
                                            emailText.value,
                                            passwordText.value
                                        )
                                    )
                                }) {
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


@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        onTriggerEvent = {}, onLoginSuccessfully = {}, isVisible = true, state = LoginState(),
        onBack = {}
    )
}