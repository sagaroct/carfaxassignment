package com.example.carfaxassignmenmt

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.carfaxassignmenmt.ui.vehiclelist.VehicleListContent
import com.example.carfaxassignmenmt.ui.vehiclelist.VehicleListUiState
import com.example.domain.models.Vehicle
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class VehicleListScreenTest {

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
    fun vehicleListScreen_showsVehicleList() {
        composeTestRule.setContent {
	        MaterialTheme {
		        VehicleListContent(
			        uiState = VehicleListUiState.Shown(
				        vehicles = listOf(sampleVehicle),
			        ),
			        onCallDealer = {},
			        onCallDialogDismissed = {},
			        onNavigationToDetailScreen = {}
		        )
	        }
        }
        composeTestRule.onNodeWithText("2020 Jeep Wrangler Unlimited Sahara 4x4").assertIsDisplayed()
	    composeTestRule.onNodeWithText("$ 35,000").assertIsDisplayed()
	    composeTestRule.onNodeWithText("45.0k mi").assertIsDisplayed()
	    composeTestRule.onNodeWithText("123 Main St, Anytown, USA").assertIsDisplayed()
	    composeTestRule.onNodeWithText("CALL DEALER").assertIsDisplayed()
    }

	@Test
	fun vehicleListContent_whenVehicleClicked_triggersNavigation() {
		var navigatedVin: String? = null
		val expectedVin = sampleVehicle.vin

		composeTestRule.setContent {
			MaterialTheme {
				VehicleListContent(
					uiState = VehicleListUiState.Shown(vehicles = listOf(sampleVehicle)),
					onCallDealer = {},
					onCallDialogDismissed = {},
					onNavigationToDetailScreen = { vin -> navigatedVin = vin }
				)
			}
		}
		composeTestRule.onNodeWithText("2020 Jeep Wrangler Unlimited Sahara 4x4").performClick()
		Assert.assertEquals(expectedVin, navigatedVin)
	}

	@Test
	fun vehicleListContent_whenCallDealerClicked_triggersCallback() {
		var calledPhoneNumber: String? = null
		val expectedPhoneNumber = sampleVehicle.phone
		composeTestRule.setContent {
			MaterialTheme {
				VehicleListContent(
					uiState = VehicleListUiState.Shown(vehicles = listOf(sampleVehicle)),
					onCallDealer = { phone -> calledPhoneNumber = phone },
					onCallDialogDismissed = {},
					onNavigationToDetailScreen = {}
				)
			}
		}
		composeTestRule.onNodeWithText("CALL DEALER").performClick()
		Assert.assertEquals(expectedPhoneNumber, calledPhoneNumber)
	}
}