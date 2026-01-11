package com.example.carfaxassignmenmt.ui.carlist

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.example.domain.models.ApiResult
import com.example.domain.models.Vehicle

@Composable
fun VehicleListScreen(
	onNavigationToDetailScreen: (id: String) -> Unit,
	vehicleListViewModel: VehicleListViewModel = hiltViewModel()
) {
	Column {
		TopAppBarComponent( title = stringResource(R.string.vehicles), showBackButton = false)
		val apiResult: ApiResult<List<Vehicle>> by vehicleListViewModel.carListApiResultFlow.collectAsStateWithLifecycle()
		when (apiResult) {
			is ApiResult.Loading -> {
				SimpleCircularProgressIndicator()
			}

			is ApiResult.Error -> {
				val errorMsg = (apiResult as ApiResult.Error).error.message ?: "Error Fetching Data"
				Toast.makeText(LocalContext.current, errorMsg, Toast.LENGTH_LONG).show()
			}

			is ApiResult.Success -> {
				VehicleListContent((apiResult as ApiResult.Success).data, onNavigationToDetailScreen)
			}
		}
	}
}

@Composable
private fun VehicleListContent(vehicles: List<Vehicle>, onNavigationToDetailScreen: (id: String) -> Unit) {
	Column {
		Modifier.background(color = Color.LightGray)
		LazyColumn {
			items(vehicles) { carListItem ->
				VehicleListItem(carListItem) {
					onNavigationToDetailScreen.invoke(carListItem.vin)
				}
			}
		}
	}
}

@SuppressLint("UnrememberedMutableState")
@Composable
private fun VehicleListItem(
	vehicle: Vehicle,
	callPhone: MutableState<Boolean> = remember { mutableStateOf(false) },
	onClick: () -> Unit
) {
	if (callPhone.value) {
		PhoneDialer().CallPhoneNumber(vehicle.phone) { dialogOpen ->
			callPhone.value = dialogOpen
		}
	}
	Surface(
		shape = RoundedCornerShape(10.dp),
		elevation = 1.dp,
		// surfaceColor color will be changing gradually from primary to surface
		color = Color.White,
		// animateContentSize will change the Surface size gradually
		modifier = Modifier
			.animateContentSize()
			.padding(5.dp).clickable {
				onClick()
			}
	) {
		Column {
			Image(
				painter = rememberAsyncImagePainter(vehicle.image),
				"Car pic",
				modifier = Modifier
					.fillMaxWidth()
					.height(250.dp),
				contentScale = ContentScale.Crop
			)
			Column(Modifier.padding(8.dp)) {
				val carPrimaryDetail =
					"${vehicle.year} ${vehicle.make} ${vehicle.model} ${vehicle.trim}"
				Text(
					text = carPrimaryDetail,
					Modifier.fillMaxWidth(),
					fontSize = 16.sp,
					fontWeight = FontWeight.Bold
				)
				Row(modifier = Modifier.padding(top = 8.dp)) {
					Text(
						text = "$ ${priceWithComma(vehicle.currentPrice)}",
						Modifier.wrapContentWidth(),
						fontSize = 16.sp
					)

					Divider(
						Modifier
							.padding(5.dp)
							.width(1.dp)
							.height(16.dp),
						color = Color.Gray, thickness = 1.5.dp
					)

					Text(
						text = "${numberWithK(vehicle.mileage)} mi",
						Modifier.wrapContentWidth(),
						fontSize = 16.sp
					)
				}
				Text(
					text = vehicle.address,
					Modifier.wrapContentWidth(),
					fontSize = 16.sp
				)

				Divider(
					Modifier.padding(5.dp, 10.dp),
					color = Color.LightGray, thickness = 1.dp
				)
				Box(
					Modifier
						.padding(5.dp)
						.wrapContentWidth()
						.align(Alignment.CenterHorizontally)
				) {
					Text(
						text = "CALL DEALER",
						Modifier.wrapContentWidth().clickable {
							callPhone.value = true
						},
						textAlign = TextAlign.Center,
						fontSize = 16.sp,
						color = Blue_Primary,
						fontWeight = FontWeight.Bold
					)
				}
			}
		}
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
	LazyColumn {
		items(carListItems) { carListItem ->
			VehicleListItem(carListItem) {
			}
		}
	}
}
