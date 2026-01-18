package com.example.carfaxassignmenmt.ui.vehiclelist

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.carfaxassignmenmt.R
import com.example.carfaxassignmenmt.common.UnitConverter.numberWithK
import com.example.carfaxassignmenmt.common.UnitConverter.priceWithComma
import com.example.carfaxassignmenmt.ui.common.SimpleCircularProgressIndicator
import com.example.carfaxassignmenmt.ui.common.TopAppBarComponent
import com.example.carfaxassignmenmt.ui.phonedialer.PhoneDialer
import com.example.carfaxassignmenmt.ui.theme.Blue_Primary
import com.example.domain.models.Vehicle

@Composable
fun VehicleListScreen(
	onNavigationToDetailScreen: (id: String) -> Unit,
	onShowSnackBar: suspend (message: String) -> Unit = {},
	vehicleListViewModel: VehicleListViewModel = hiltViewModel()
) {
	Column {
		TopAppBarComponent(title = stringResource(R.string.vehicles), showBackButton = false)
		val uiState by vehicleListViewModel.uiState.collectAsStateWithLifecycle()
		when(uiState) {
			VehicleListUiState.Loading -> {
				SimpleCircularProgressIndicator()
			}
			is VehicleListUiState.Error -> {
				VehicleListErrorScreen(message = (uiState as VehicleListUiState.Error).message)
			}
			is VehicleListUiState.Shown -> {
				VehicleListContent(
					uiState = uiState as VehicleListUiState.Shown,
					onCallDealer = {vehicleListViewModel.sendUiEvent(VehicleListUiEvent.CallDealerCTA(phoneNumber = it))},
					onCallDialogDismissed = { vehicleListViewModel.sendUiEvent(VehicleListUiEvent.CallDialogDismissed) },
					onNavigationToDetailScreen = onNavigationToDetailScreen
				)
			}
		}
	}
	LaunchedEffect(Unit){
		vehicleListViewModel.sideEffect.collect { sideEffect ->
			when(sideEffect) {
				is VehicleListSideEffect.ShowErrorMessage -> {
					onShowSnackBar(sideEffect.message)
				}
			}
		}

	}
}

@Composable
 fun VehicleListContent(
	uiState: VehicleListUiState.Shown,
	onCallDealer: (String) -> Unit,
	onCallDialogDismissed: () -> Unit,
	onNavigationToDetailScreen: (id: String) -> Unit
) {
	 val vehicles = uiState.vehicles
	if (uiState.shouldShowCallDialog) {
		PhoneDialer().CallPhoneNumber(uiState.selectedPhoneNumber) { dialogOpen ->
			if (!dialogOpen) onCallDialogDismissed()
		}
	}
	Column {
		Modifier.background(color = Color.LightGray)
		LazyColumn {
			items(vehicles) { carListItem ->
				VehicleListItem(carListItem, onCallDealer) {
					onNavigationToDetailScreen.invoke(carListItem.vin)
				}
			}
		}
	}
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun VehicleListItem(
	vehicle: Vehicle,
	onCallDealer: (String) -> Unit,
	onClick: () -> Unit
) {
	Card(
		shape = RoundedCornerShape(10.dp),
		elevation = 1.dp,
		modifier = Modifier
			.animateContentSize()
			.padding(5.dp),
		onClick = onClick
	) {
		Column {
			VehicleImage(vehicle.image)
			VehicleDetails(vehicle, onCallDealer = { onCallDealer(vehicle.phone) })
		}
	}
}

@Composable
private fun VehicleImage(imageUrl: String) {
	Image(
		painter = rememberAsyncImagePainter(imageUrl),
		contentDescription = "Car image",
		modifier = Modifier
			.fillMaxWidth()
			.height(250.dp),
		contentScale = ContentScale.Crop
	)
}

@Composable
private fun VehicleDetails(vehicle: Vehicle, onCallDealer: () -> Unit) {
	Column(Modifier.padding(8.dp)) {
		VehicleTitle(vehicle)
		PriceAndMileageRow(vehicle.currentPrice, vehicle.mileage)
		VehicleAddress(vehicle.address)
		Divider(
			Modifier.padding(top = 5.dp),
			color = Color.LightGray,
			thickness = 1.dp
		)
		CallDealerButton(onCallDealer)
	}
}

@Composable
private fun VehicleTitle(vehicle: Vehicle) {
	Text(
		text = "${vehicle.year} ${vehicle.make} ${vehicle.model} ${vehicle.trim}",
		modifier = Modifier.fillMaxWidth(),
		fontSize = 16.sp,
		fontWeight = FontWeight.Bold
	)
}

@Composable
private fun PriceAndMileageRow(price: Int, mileage: Int) {
	Row(modifier = Modifier.padding(top = 8.dp)) {
		Text(
			text = "$ ${priceWithComma(price)}",
			fontSize = 16.sp
		)
		Divider(
			Modifier
				.padding(horizontal = 5.dp)
				.width(1.dp)
				.height(16.dp),
			color = Color.Gray,
			thickness = 1.5.dp
		)
		Text(
			text = "${numberWithK(mileage)} mi",
			fontSize = 16.sp
		)
	}
}

@Composable
private fun VehicleAddress(address: String) {
	Text(
		text = address,
		fontSize = 16.sp,
		modifier = Modifier.padding(top = 4.dp)
	)
}

@Composable
private fun CallDealerButton(onClick: () -> Unit) {
	TextButton(
		onClick = onClick,
		modifier = Modifier
			.fillMaxSize()
	) {
		Text(
			text = "CALL DEALER",
			textAlign = TextAlign.Center,
			fontSize = 16.sp,
			color = Blue_Primary,
			fontWeight = FontWeight.Bold
		)
	}
}

@Composable
private fun VehicleListErrorScreen(message: String) {
	Column(
		modifier = Modifier
			.fillMaxSize(),
		horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
		verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
	) {
		Text(
			text = message,
			fontSize = 18.sp,
			color = Color.Red,
			textAlign = TextAlign.Center
		)
	}
}


@Preview
@Composable
fun PreviewVehicleList() {
	val vehicle = Vehicle(
		vin = "1C4RJFBG1LC123456",
		transmission = "Automatic",
		mileage = 45000,
		image = "https://cdn.carfax.com/images/carfx/vehicles/640/1C4RJFBG1LC123456/2020_Jeep_Wrangler_Unlimited_Sahara_4x4_640_01.jpg",
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
	val carListItems = arrayListOf(vehicle, vehicle, vehicle)
	VehicleListContent(
		uiState = VehicleListUiState.Shown(
			vehicles = carListItems,
			shouldShowCallDialog = false,
			selectedPhoneNumber = ""
		),
		onCallDealer = {},
		onCallDialogDismissed = {},
		onNavigationToDetailScreen = {}
	)
}

@Preview
@Composable
private fun PreviewErrorScreen() {
	VehicleListErrorScreen(message = "Unable to load vehicles. Please try again later.")
}
