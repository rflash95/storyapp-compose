package com.rzamau.storyapp.presentation.register

import android.widget.Toast
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
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rzamau.storyapp.R
import com.rzamau.storyapp.presentation.component.*
import com.rzamau.storyapp.presentation.theme.StoryAppTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterScreen(
    state: RegisterState,
    onRegisterSuccessfully: () -> Unit,
    onTriggerEvent: (RegisterEvents) -> Unit,
    onBack: () -> Unit,
    isVisible: Boolean = false

) {
    StoryAppTheme {

        val nameText = remember { mutableStateOf("") }
        val emailText = remember { mutableStateOf("") }
        val passwordText = remember { mutableStateOf("") }
        val confirmPasswordText = remember { mutableStateOf("") }

        val isVisibleField = rememberSaveable { mutableStateOf(isVisible) }
        val hasAlreadyNavigated = remember { mutableStateOf(false) }
        val keyboardController = LocalSoftwareKeyboardController.current
        val context = LocalContext.current



        LaunchedEffect(Unit) {
            isVisibleField.value = true
        }


        if (!hasAlreadyNavigated.value && state.isRegisterSuccessfully) {
            onRegisterSuccessfully()
            hasAlreadyNavigated.value = true
            Toast.makeText(
                context,
                stringResource(R.string.account_has_been_created),
                Toast.LENGTH_SHORT
            ).show()
        }

        Scaffold(topBar = {
            TopBar(
                title = stringResource(R.string.register),
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
                            .padding(16.dp)
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
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(140.dp)
                                        .clip(CircleShape)
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
                            if (message == "Email is already taken") {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(20.dp))
                                        .background(Color.Red.copy(alpha = 0.8f))
                                ) {
                                    Text(
                                        modifier = Modifier.padding(8.dp),
                                        text = stringResource(R.string.email_already_registered),
                                        style = MaterialTheme.typography.caption,
                                        color = Color.White
                                    )
                                }
                            }
                        }
                        Box(modifier = Modifier.height(80.dp)) {
                            this@Column.AnimatedVisibility(
                                visible = isVisibleField.value,
                                enter = fadeIn(tween(3000))
                            ) {
                                NameTextField(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    value = nameText.value,
                                    onValueChange = { value ->
                                        nameText.value = value
                                    },
                                    label = {
                                        Text(text = stringResource(R.string.name))
                                    },
                                    singleLine = true,
                                    leadingIcon = {
                                        IconButton(onClick = {}) {
                                            Icon(
                                                imageVector = Icons.Filled.Person,
                                                contentDescription = stringResource(R.string.name_icon)
                                            )
                                        }
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Next
                                    ),
                                )
                            }
                        }

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
                                                contentDescription = stringResource(R.string.email_icon)
                                            )
                                        }
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Email,
                                        imeAction = ImeAction.Next
                                    ),

                                    )
                            }
                        }


                        Box(modifier = Modifier.height(80.dp)) {
                            this@Column.AnimatedVisibility(
                                visible = isVisibleField.value,
                                enter = fadeIn(tween(3000))
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
                                                contentDescription = stringResource(R.string.password_icon)
                                            )
                                        }
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Password,
                                        imeAction = ImeAction.Next
                                    ),
                                    visualTransformation = PasswordVisualTransformation()
                                )
                            }
                        }

                        Box(modifier = Modifier.height(80.dp)) {
                            this@Column.AnimatedVisibility(
                                visible = isVisibleField.value,
                                enter = fadeIn(tween(3500))
                            ) {
                                ConfirmPasswordTextField(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    value = confirmPasswordText.value,
                                    valuePassword = passwordText.value,
                                    onValueChange = { value ->
                                        confirmPasswordText.value = value
                                    },
                                    label = {
                                        Text(text = stringResource(R.string.confirm_password))
                                    },
                                    singleLine = true,
                                    leadingIcon = {
                                        IconButton(onClick = {}) {
                                            Icon(
                                                imageVector = Icons.Filled.Lock,
                                                contentDescription = stringResource(R.string.password_icon)
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
                                        RegisterEvents.OnPostRegister(
                                            name = nameText.value,
                                            email = emailText.value,
                                            password = passwordText.value,
                                            confirmPassword = confirmPasswordText.value
                                        )
                                    )
                                }) {
                                    Text(text = stringResource(R.string.register))
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
fun RegisterScreenPreview() {
    RegisterScreen(
        state = RegisterState(),
        onRegisterSuccessfully = {},
        onTriggerEvent = {},
        isVisible = true, onBack = {}
    )
}