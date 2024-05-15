package com.example.cupcake.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.cupcake.CupcakeApp
import com.example.cupcake.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CupcakeSummaryScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setupCupcakeNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            CupcakeApp(navController = navController)
        }
    }

    @Test
    fun summaryScreen_verifyContent() {
        navigateToSummaryScreen()
        composeTestRule.onNodeWithText("QUANTITY").assertIsDisplayed()
        composeTestRule.onNodeWithText("1 cupcake").assertIsDisplayed()

        val flavorText = composeTestRule.activity.getString(R.string.flavor).uppercase()
        composeTestRule.onNodeWithText(flavorText).assertIsDisplayed()
        composeTestRule.onNodeWithStringId(R.string.chocolate).assertIsDisplayed()

        val pickupDateText = composeTestRule.activity.getString(R.string.pickup_date).uppercase()
        composeTestRule.onNodeWithText(pickupDateText).assertIsDisplayed()
        composeTestRule.onNodeWithText(getFormattedDate()).assertIsDisplayed()

        val subtotalPrice = composeTestRule.activity.getString(R.string.subtotal_price, "$2.00")
        composeTestRule.onNodeWithText(subtotalPrice).assertIsDisplayed()

        composeTestRule.onNodeWithStringId(R.string.send).assertIsDisplayed()
        composeTestRule.onNodeWithStringId(R.string.cancel).assertIsDisplayed()
    }

    private fun navigateToSummaryScreen() {
        composeTestRule.onNodeWithStringId(R.string.one_cupcake).performClick()
        composeTestRule.onNodeWithStringId(R.string.chocolate).performClick()
        composeTestRule.onNodeWithStringId(R.string.next).performClick()
        composeTestRule.onNodeWithText(getFormattedDate()).performClick()
        composeTestRule.onNodeWithStringId(R.string.next).performClick()
        composeTestRule.waitForIdle()
    }

    private fun getFormattedDate(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, 1)
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        return formatter.format(calendar.time)
    }
}