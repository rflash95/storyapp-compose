package com.rzamau.storyapp.presentation.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.rzamau.storyapp.MainDispatcherRule
import com.rzamau.storyapp.domain.model.User
import com.rzamau.storyapp.domain.util.DataState
import com.rzamau.storyapp.interactors.login.PostLogin
import com.rzamau.storyapp.utils.DataDummy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner


@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var loginViewModel: LoginViewModel


    @Mock
    private lateinit var savedStateHandle: SavedStateHandle

    @Mock
    private lateinit var postLogin: PostLogin

    private val loginResponseMock = DataDummy.loginResponse()
    private val emailMock = "test@email.com"
    private val passwordMock = "12345678"

    @Before
    fun setup() {
        loginViewModel = LoginViewModel(
            savedStateHandle = savedStateHandle,
            postLogin = postLogin
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @ExperimentalCoroutinesApi
    @Test
    fun `when login success`() = runTest {
        val state = loginViewModel.state

        val expectedResponse: Flow<DataState<User>> = flow {
            emit(DataState.data(data = loginResponseMock))
        }


        `when`(postLogin.execute(email = emailMock, password = passwordMock))
            .thenReturn(expectedResponse)

        loginViewModel.onTriggerEvent(
            LoginEvents.OnPostLogin(
                email = emailMock,
                password = passwordMock
            )
        )

        verify(postLogin).execute(email = emailMock, password = passwordMock)
        assertTrue(state.value.isLoginSuccessfully)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when login failed`() = runTest {

        val state = loginViewModel.state
        val expectedResponse: Flow<DataState<User>> = flow {
            emit(DataState.error(message = "login failed"))
        }

        `when`(postLogin.execute(email = emailMock, password = passwordMock))
            .thenReturn(expectedResponse)

        loginViewModel.onTriggerEvent(
            LoginEvents.OnPostLogin(
                email = emailMock,
                password = passwordMock
            )
        )

        verify(postLogin).execute(email = emailMock, password = passwordMock)
        assertFalse(state.value.isLoginSuccessfully)
    }
}
