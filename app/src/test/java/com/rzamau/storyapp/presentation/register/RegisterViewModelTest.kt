package com.rzamau.storyapp.presentation.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.rzamau.storyapp.MainDispatcherRule
import com.rzamau.storyapp.domain.util.DataState
import com.rzamau.storyapp.interactors.register.PostRegister
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class RegisterViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var registerViewModel: RegisterViewModel

    @Mock
    private lateinit var savedStateHandle: SavedStateHandle

    @Mock
    private lateinit var postRegister: PostRegister

    private val nameMock = "test"
    private val emailMock = "test@email.com"
    private val passwordMock = "12345678"
    private val confirmPasswordMock = "12345678"

    @Before
    fun setup() {
        registerViewModel = RegisterViewModel(
            savedStateHandle = savedStateHandle,
            postRegister = postRegister
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @ExperimentalCoroutinesApi
    @Test
    fun `when Register Success`() = runTest {
        val state = registerViewModel.state
        val expectedResponse: Flow<DataState<Boolean>> = flow {
            emit(DataState.data(data = true))
        }

        Mockito.`when`(
            postRegister.execute(
                name = nameMock,
                email = emailMock,
                password = passwordMock
            )
        ).thenReturn(expectedResponse)

        registerViewModel.onTriggerEvent(
            RegisterEvents.OnPostRegister(
                name = nameMock,
                email = emailMock,
                password = passwordMock,
                confirmPassword = confirmPasswordMock
            )
        )

        Mockito.verify(postRegister)
            .execute(name = nameMock, email = emailMock, password = passwordMock)
        assertTrue(state.value.isRegisterSuccessfully)
    }
}