package com.example.carfaxassignmenmt.ui.vehicledetail

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.carfaxassignmenmt.common.UnitConverter
import com.example.carfaxassignmenmt.ui.common.SimpleCircularProgressIndicator
import com.example.carfaxassignmenmt.ui.common.TopAppBarComponent
import com.example.carfaxassignmenmt.ui.phonedialer.PhoneDialer
import com.example.carfaxassignmenmt.ui.theme.Blue_Primary
import com.example.domain.models.Vehicle


@Composable
fun VehicleDetailScreen(vin: String, onBackClick: () -> Unit) {
	val vehicleDetailViewModel: VehicleDetailViewModel = hiltViewModel()
	Column {
		TopAppBarComponent(title = vin, onBackClick = onBackClick)
		val uiState by vehicleDetailViewModel.uiState.collectAsStateWithLifecycle()

		when {
			uiState.isLoading -> {
				SimpleCircularProgressIndicator()
			}

			uiState.error != null -> {
				Toast.makeText(LocalContext.current, uiState.error, Toast.LENGTH_LONG).show()
			}

			uiState.vehicle != null -> {
				VehicleDetailContent(
					uiState = uiState,
					onCallDealer = vehicleDetailViewModel::onCallDealerClicked,
					onCallDialogDismissed = vehicleDetailViewModel::onCallDialogDismissed
				)
			}
		}
	}
	LaunchedEffect(Unit) {
		vehicleDetailViewModel.getVehicle(vin)
	}
}

@Composable
fun VehicleDetailContent(
	uiState: VehicleDetailUiState,
	onCallDealer: (String) -> Unit,
	onCallDialogDismissed: () -> Unit
) {
	if (uiState.shouldShowCallDialog) {
		PhoneDialer().CallPhoneNumber(uiState.selectedPhoneNumber) { dialogOpen ->
			if (!dialogOpen) onCallDialogDismissed()
		}
	}
	val vehicle = uiState.vehicle ?: return

	Surface(
		color = Color.White,
		modifier = Modifier
			.animateContentSize()
			.fillMaxHeight()
	) {
		Column(modifier = Modifier.fillMaxHeight()) {
			Column(
				modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
			) {
				VehicleDetailImage(vehicle.image)
				VehicleDetailHeader(vehicle)
				DetailDivider()
				VehicleInfoSection(vehicle)
			}
			CallDealerSection(onCallDealer = { onCallDealer(vehicle.phone) })
		}
	}
}

@Composable
private fun VehicleDetailImage(imageUrl: String) {
	Image(
		painter = rememberAsyncImagePainter(imageUrl),
		contentDescription = "Car pic",
		modifier = Modifier
			.fillMaxWidth()
			.height(250.dp),
		contentScale = ContentScale.Crop
	)
}

@Composable
private fun VehicleDetailHeader(vehicle: Vehicle) {
	Column(Modifier.padding(start = 30.dp, top = 10.dp)) {
		Text(
			text = "${vehicle.year} ${vehicle.make} ${vehicle.model} ${vehicle.trim}",
			modifier = Modifier.fillMaxWidth(),
			fontSize = 16.sp,
			color = Color.DarkGray,
			fontWeight = FontWeight.Bold
		)
		PriceAndMileageRow(vehicle.currentPrice, vehicle.mileage)
	}
}

@Composable
private fun PriceAndMileageRow(price: Int, mileage: Int) {
	Row(modifier = Modifier.padding(top = 8.dp)) {
		Text(
			text = "$ ${UnitConverter.priceWithComma(price)}",
			fontSize = 22.sp,
			fontWeight = FontWeight.Bold
		)
		Divider(
			Modifier
				.padding(5.dp)
				.width(3.dp)
				.height(20.dp),
			color = Color.Black
		)
		Text(
			text = "${UnitConverter.numberWithK(mileage)} mi",
			fontSize = 22.sp,
			fontWeight = FontWeight.Bold
		)
	}
}

@Composable
private fun DetailDivider() {
	Divider(
		Modifier.padding(start = 10.dp, end = 10.dp, top = 20.dp),
		color = Color.LightGray,
		thickness = 1.dp
	)
}

@Composable
private fun VehicleInfoSection(vehicle: Vehicle) {
	Column(
		Modifier
			.padding(start = 30.dp, top = 10.dp)
	) {
		Text(
			text = "Vehicle Info",
			modifier = Modifier.padding(bottom = 20.dp),
			fontSize = 16.sp,
			fontWeight = FontWeight.Bold
		)
		VehicleInfoRow("Location", vehicle.address)
		VehicleInfoRow("Exterior Color", vehicle.exteriorColor)
		VehicleInfoRow("Interior Color", vehicle.interiorColor)
		VehicleInfoRow("Drive Type", vehicle.drivetype)
		VehicleInfoRow("Transmission", vehicle.transmission)
		VehicleInfoRow("Body Style", vehicle.bodytype)
		VehicleInfoRow("Engine", vehicle.engine)
		VehicleInfoRow("Fuel", vehicle.fuel)
	}
}

@Composable
private fun VehicleInfoRow(label: String, value: String) {
	Row {
		Text(
			text = label,
			Modifier.requiredWidthIn(130.dp),
			fontSize = 16.sp,
			color = Color.Gray
		)
		Text(
			text = value,
			Modifier.wrapContentWidth(),
			fontSize = 16.sp
		)
	}
}

@Composable
private fun CallDealerSection(onCallDealer: () -> Unit) {
	Button(
		onClick = onCallDealer,
		modifier = Modifier
			.fillMaxWidth()
			.height(50.dp),
		colors = ButtonDefaults.buttonColors(backgroundColor = Blue_Primary),
		shape = RectangleShape
	) {
		Text(text = "CALL DEALER", color = Color.White)
	}
}

@Preview
@Composable
private fun PreviewVehicleDetailContent() {
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
	VehicleDetailContent(
		uiState = VehicleDetailUiState(
			isLoading = false,
			vehicle = vehicle,
			shouldShowCallDialog = false,
			selectedPhoneNumber = ""
		),
		onCallDealer = {},
		onCallDialogDismissed = {}
	)
}
