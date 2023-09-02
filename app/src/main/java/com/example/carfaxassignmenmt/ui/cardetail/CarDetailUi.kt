package com.example.carfaxassignmenmt.ui.cardetail

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.carfaxassignmenmt.R
import com.example.carfaxassignmenmt.common.UnitConverter
import com.example.carfaxassignmenmt.ui.carlist.CarListViewModel
import com.example.carfaxassignmenmt.ui.common.CommonComposeUi
import com.example.carfaxassignmenmt.ui.phonedialer.PhoneDialer
import com.example.carfaxassignmenmt.ui.theme.Blue_Primary
import com.example.domain.models.ApiResult
import com.example.domain.models.CarItem

/**
 * Created by Sagar Pujari on 02/10/22.
 */
class CarDetailUi {

    companion object {
        private const val TAG = "CarDetailUi"
    }

    private val callPhone = mutableStateOf(false)

    @Composable
    fun TopAppBarComponent() {
        TopAppBar(
            modifier = Modifier
                .fillMaxWidth(),
            title = { Text(stringResource(id = R.string.app_name)) }
        )
    }

    @SuppressLint("UnrememberedMutableState")
    @Composable
    fun CarDetail(carItem: CarItem){
            if(callPhone.value){
            PhoneDialer().CallPhoneNumber(carItem.phone){ dialogOpen ->
                callPhone.value = dialogOpen
            }
        }
        Surface(
                // surfaceColor color will be changing gradually from primary to surface
                color = Color.White,
                // animateContentSize will change the Surface size gradually
                modifier = Modifier
                    .animateContentSize()
                    .fillMaxHeight()
//                    .verticalScroll(rememberScrollState())

            ) {
                Column(modifier = Modifier.verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.SpaceBetween) {
                    Image(
                        painter =  rememberAsyncImagePainter(
                            carItem.image),
                        "Car pic",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        contentScale = ContentScale.Crop
                    )
                    Column(Modifier.padding(start = 30.dp, top = 10.dp)) {
                        val carPrimaryDetail =
                            "${carItem.year} ${carItem.make} ${carItem.model} ${carItem.trim}"
                        Text(
                            text = carPrimaryDetail,
                            Modifier.fillMaxWidth(),
                            fontSize = 16.sp,
                            color = Color.DarkGray,
                            fontWeight = FontWeight.Bold
                        )
                        Row(modifier = Modifier.padding(top = 8.dp)) {
                            Text(
                                text = "$ ${UnitConverter.priceWithComma(carItem.currentPrice)}",
                                Modifier.wrapContentWidth(),
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
                                text = "${UnitConverter.numberWithK(carItem.mileage)} mi",
                                Modifier.wrapContentWidth(),
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Divider(
                        Modifier.padding(start = 10.dp, end = 10.dp, top = 20.dp),
                        color = Color.LightGray, thickness = 1.dp
                    )

                    Column(Modifier.padding(start = 30.dp, top = 10.dp).weight(3f, false)) {
                        Text(
                            text = "Vehicle Info",
                            Modifier
                                .wrapContentWidth()
                                .padding(bottom = 30.dp),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )

                        VehicleInfoRow("Location", carItem.address)
                        VehicleInfoRow("Exterior Color", carItem.exteriorColor)
                        VehicleInfoRow("Interior Color", carItem.interiorColor)
                        VehicleInfoRow("Drive Type", carItem.drivetype)
                        VehicleInfoRow("Transmission", carItem.transmission)
                        VehicleInfoRow("Body Style", carItem.bodytype)
                        VehicleInfoRow("Engine", carItem.engine)
                        VehicleInfoRow("Fuel", carItem.fuel)
                    }
                    Divider(
                        Modifier
                            .padding(start = 10.dp, end = 10.dp, top = 30.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Gray,
                                        Color.White
                                    )
                                )
                            ), thickness = 4.dp
                    )
                    Column(
                        Modifier
                            .padding(top = 300.dp)
                            .align(Alignment.CenterHorizontally)
                            .weight(1f, false)
                    ) {
                        Button(
                            onClick = {
                                callPhone.value = true
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Blue_Primary),
                            shape  = RectangleShape,
                        )
                        {
                            Text(text = "CALL DEALER", color = Color.White)
                        }

                    }
                }
            }
    }

    @Composable
    fun VehicleInfoRow(label:String, value: String){
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
    fun MainContent(carListItemId: String){
        val carListViewModel: CarListViewModel = hiltViewModel()
        Column {
            TopAppBarComponent()
            val apiResult: ApiResult<CarItem> by carListViewModel.carItemApiResultFlow.collectAsStateWithLifecycle()
            when (apiResult) {
                is ApiResult.Loading -> {
                    CommonComposeUi.SimpleCircularProgressIndicator()
                }
                is ApiResult.Error -> {
                    Toast.makeText(LocalContext.current, "Error Fetching Data", Toast.LENGTH_LONG).show()
                }
                is ApiResult.Success -> {
                    Log.d(TAG, "apiResult: ${(apiResult as ApiResult.Success).data}")
                    CarDetail((apiResult as ApiResult.Success).data)
                }
            }
            }
        carListViewModel.getCarItemFromRepository(carListItemId)
        Log.d(TAG , "Called getCarItemFromRepository")
    }

    @Preview
    @Composable
    fun PreviewCarDetail() {
        val carItem = CarItem(
            "19UDE2F3XGA025865",
            "Automatic",
            22,
            "https://firebasestorage.googleapis.com/v0/b/carfax-for-consumers.appspot.com/o/1024x768%2Fabarth-fiat-124-spider-fiat-2011.jpeg?alt=media&token=9275628b-d052-44f8-8cb1-38ffb69bff66",
            "Gray",
            "FWD",
            "4 Cyl",
            "Sedan",
            "Gray",
            15599,
            "8663014042",
            "10385 Central Ave",
            2006,
            "Acura",
            "ILX",
            "Unspecified",
            "Petrol"
        )
        CarDetail(carItem = carItem)
    }

}