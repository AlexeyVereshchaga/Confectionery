package com.sun.confectionery.auth.presentation

import app.cash.turbine.test
import com.sun.confectionery.auth.domain.model.Token
import com.sun.confectionery.auth.domain.usecase.LoginUseCase
import com.sun.confectionery.auth.domain.usecase.RegisterUseCase
import com.sun.confectionery.auth.presentation.util.MainCoroutineRule
import com.sun.confectionery.core.Outcome
import com.sun.confectionery.core.navigation.NavigationEvent
import com.sun.confectionery.core.navigation.NavigationManager
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AuthViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: AuthViewModel
    private val navigationManager: NavigationManager = mockk(relaxed = true)
    private val loginUseCase: LoginUseCase = mockk()
    private val registerUseCase: RegisterUseCase = mockk()

    @Before
    fun setUp() {
        viewModel = AuthViewModel(navigationManager, loginUseCase, registerUseCase)
    }

    @Test
    fun `initial state is correct`() = runTest {
        val initialState = AuthState()
        assertEquals(initialState, viewModel.state.value)
    }

    @Test
    fun `login success navigates to products and sends success event`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password"
        coEvery { loginUseCase(email, password) } returns Outcome.Success(Token("", ""))

        viewModel.events.test {
            // When
            viewModel.handleIntent(AuthIntent.Login(email, password))

            // Then
            assertFalse(viewModel.state.value.isLoading)

            // Check event
            assertEquals(AuthEvent.Success, awaitItem())

            // Check navigation
            coVerify { navigationManager.navigate(NavigationEvent.NavigateToProducts) }
        }
    }

    @Test
    fun `login failure sends error event`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "wrongpassword"
        val errorMessage = "Invalid credentials"
        coEvery { loginUseCase(email, password) } returns Outcome.Error(Exception(errorMessage))

        viewModel.events.test {
            // When
            viewModel.handleIntent(AuthIntent.Login(email, password))

            // Then
            assertFalse(viewModel.state.value.isLoading)

            // Check event
            val event = awaitItem()
            assertTrue(event is AuthEvent.Error)
            assertEquals(errorMessage, (event as AuthEvent.Error).message)

            // Verify navigation did not happen
            coVerify(exactly = 0) { navigationManager.navigate(any()) }
        }
    }
}
