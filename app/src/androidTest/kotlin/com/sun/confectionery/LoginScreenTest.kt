package com.sun.confectionery

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun loginScreen_whenLoginIsClicked_buttonTextChangesToLoading() {
        // Input email and password
        composeTestRule.onNodeWithTag("login_email_input").performTextInput("test@email.com")
        composeTestRule.onNodeWithTag("login_password_input").performTextInput("password")

        // Click the button
        composeTestRule.onNodeWithTag("login_button").performClick()

        // The onNodeWithText function will wait for the node to appear.
        // This is how we handle the asynchronous nature of the UI update.
        composeTestRule.onNodeWithText("Loading...").assertIsDisplayed()
    }
}
