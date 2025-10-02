package com.sun.confectionery

import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
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
        // Ввод email и пароля
        composeTestRule.onNodeWithTag("login_email_input").performTextInput("test@email.com")
        composeTestRule.onNodeWithTag("login_password_input").performTextInput("password")

        // Нажатие на кнопку
        composeTestRule.onNodeWithTag("login_button").performClick()

        // Функция onNodeWithText будет ждать появления узла.
        // Так мы обрабатываем асинхронную природу обновления UI.
        composeTestRule.onNodeWithText("Loading...").assertIsDisplayed()
    }

    @Test
    fun e2e_loginAndNavigateToProductDetail_verifiesProductName() {
        // Логин
        composeTestRule.onNodeWithTag("login_email_input").performTextInput("avereshchaga@yandex.ru")
        composeTestRule.onNodeWithTag("login_password_input").performTextInput("123456")
        composeTestRule.onNodeWithTag("login_button").performClick()

        // Ожидание загрузки экрана продуктов
        composeTestRule.waitUntil(20_000) {
            composeTestRule.onAllNodesWithTag("product_item").fetchSemanticsNodes().isNotEmpty()
        }

        // Получение названия первого продукта
        val firstProductNameNode = composeTestRule.onAllNodesWithTag("product_name").onFirst()
        val productNameOnList = firstProductNameNode.fetchSemanticsNode().config[SemanticsProperties.Text][0].text

        // Нажатие на первый продукт
        composeTestRule.onAllNodesWithTag("product_item").onFirst().performClick()

        // Ожидание экрана деталей продукта и проверка названия
        composeTestRule.waitUntil(25_000) {
            composeTestRule.onAllNodesWithTag("product_detail_name").fetchSemanticsNodes().isNotEmpty()
        }
        composeTestRule.onNodeWithTag("product_detail_name").assertTextEquals(productNameOnList)
    }
}