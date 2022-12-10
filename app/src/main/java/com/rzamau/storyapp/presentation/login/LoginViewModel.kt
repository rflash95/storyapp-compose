package com.rzamau.storyapp.presentation.login

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rzamau.storyapp.interactors.login.PostLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val savedStateHandle: SavedStateHandle,
    private val postLogin: PostLogin
) : ViewModel() {
    val TAG = "LoginViewModel"

    val state: MutableState<LoginState> = mutableStateOf(value = LoginState())

    fun onTriggerEvent(event: LoginEvents) {
        when (event) {
            is LoginEvents.OnPostLogin -> {
                onPostLogin(event.email, event.password)
            }
        }
    }

    private fun onPostLogin(email: String, password: String) {
        postLogin.execute(
            email = email,
            password = password
        ).onEach { dataState ->
            state.value = state.value.copy(isLoading = dataState.isLoading)
            dataState.data?.let { _ ->
                state.value = state.value.copy(isLoginSuccessfully = true)
            }
            dataState.message?.let { message ->
                state.value = state.value.copy(message = message)
                delay(5000)
                state.value = state.value.copy(message = "")
            }
        }.launchIn(viewModelScope)
    }
}