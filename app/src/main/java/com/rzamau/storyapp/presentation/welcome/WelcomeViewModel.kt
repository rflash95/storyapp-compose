package com.rzamau.storyapp.presentation.welcome

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rzamau.storyapp.interactors.pref.GetUserPref
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel
@Inject
constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getUserPref: GetUserPref
) : ViewModel() {

    val state: MutableState<WelcomeState> = mutableStateOf(value = WelcomeState())

    init {
        checkIsLogin()
    }


    private fun checkIsLogin() {
        getUserPref.execute().onEach { dataState ->
            dataState.data?.let { user ->
                if (user.token.isNotEmpty()) {
                    delay(2000)
                    state.value = state.value.copy(isLogin = true, user = user, isLoading = false)
                } else {
                    state.value = state.value.copy(isLogin = false, user = user, isLoading = false)
                }
            }
            dataState.message?.let { _ ->

            }
        }.launchIn(viewModelScope)
    }


}