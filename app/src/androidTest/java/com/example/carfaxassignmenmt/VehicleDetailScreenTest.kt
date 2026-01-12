package com.example.carfaxassignmenmt

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.carfaxassignmenmt.ui.vehicledetail.VehicleDetailContent
import com.example.carfaxassignmenmt.ui.vehicledetail.VehicleDetailUiState
import com.example.domain.models.Vehicle
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class VehicleDetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val sampleVehicle = Vehicle(
        vin = "1C4RJFBG1LC123456",
        transmission = "Automatic",
        mileage = 45000,
        image = "",
        interiorColor = "Black",
        drivetype = "4WD",
        engine = "3.6L V6",
        bodytype = "SUV",
        exteriorColor = "Red",
        currentPrice = 35000,
        phone = "123-456-7890",
        address = "123 Main St, Anytown, USA",
        year = 2020,
        make = "Jeep",
        model = "Wrangler Unlimited",
        trim = "Sahara 4x4",
        fuel = "Gasoline"
    )

    @Test
    fun vehicleDetailContent_displaysVehicleInformation() {
        composeTestRule.setContent {
            MaterialTheme {
                VehicleDetailContent(
                    uiState = VehicleDetailUiState(vehicle = sampleVehicle),
                    onCallDealer = {},
                    onCallDialogDismissed = {}
                )
            }
        }

        // Assert Header Info
        composeTestRule.onNodeWithText("2020 Jeep Wrangler Unlimited Sahara 4x4").assertIsDisplayed()
        composeTestRule.onNodeWithText("$ 35,000").assertIsDisplayed()
        composeTestRule.onNodeWithText("45.0k mi").assertIsDisplayed()

        // Assert Vehicle Info Section
        composeTestRule.onNodeWithText("Vehicle Info").assertIsDisplayed()
        composeTestRule.onNodeWithText("123 Main St, Anytown, USA").assertIsDisplayed()
        composeTestRule.onNodeWithText("Red").assertIsDisplayed()
        composeTestRule.onNodeWithText("Black").assertIsDisplayed()
        composeTestRule.onNodeWithText("4WD").assertIsDisplayed()
        composeTestRule.onNodeWithText("Automatic").assertIsDisplayed()
        composeTestRule.onNodeWithText("SUV").assertIsDisplayed()
        composeTestRule.onNodeWithText("3.6L V6").assertIsDisplayed()
        composeTestRule.onNodeWithText("Gasoline").assertIsDisplayed()

        // Assert Call Dealer Button
        composeTestRule.onNodeWithText("CALL DEALER").assertIsDisplayed()
    }

    @Test
    fun vehicleDetailContent_whenCallDealerClicked_triggersCallback() {
        var calledPhoneNumber: String? = null
        val expectedPhoneNumber = sampleVehicle.phone

        composeTestRule.setContent {
            MaterialTheme {
                VehicleDetailContent(
                    uiState = VehicleDetailUiState(vehicle = sampleVehicle),
                    onCallDealer = { phone -> calledPhoneNumber = phone },
                    onCallDialogDismissed = {}
                )
            }
        }

        composeTestRule.onNodeWithText("CALL DEALER").performClick()

        assertEquals(expectedPhoneNumber, calledPhoneNumber)
    }
}
