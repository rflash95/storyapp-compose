package com.rzamau.storyapp.presentation.register

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rzamau.storyapp.interactors.register.PostRegister
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
@Inject
constructor(
    private val savedStateHandle: SavedStateHandle,
    private val postRegister: PostRegister
) : ViewModel() {
    val TAG = "RegisterViewModel"

    val state: MutableState<RegisterState> = mutableStateOf(value = RegisterState())

    fun onTriggerEvent(event: RegisterEvents) {
        when (event) {
            is RegisterEvents.OnPostRegister -> {
                onPostRegister(event.name, event.email, event.password)
            }
        }
    }


    private fun onPostRegister(name: String, email: String, password: String) {
        postRegister.execute(
            name = name,
            email = email,
            password = password
        ).onEach { dataState ->
            state.value = state.value.copy(isLoading = dataState.isLoading)
            dataState.data?.let { _ ->
                state.value = state.value.copy(isRegisterSuccessfully = true)
            }

            dataState.message?.let { message ->
                state.value = state.value.copy(message = message)
                delay(5000)
                state.value = state.value.copy(message = "")
            }
        }.launchIn(viewModelScope)
    }
}