package com.example.carfaxassignmenmt.ui.carlist

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.carfaxassignmenmt.R
import com.example.carfaxassignmenmt.common.Constants
import com.example.carfaxassignmenmt.common.UnitConverter.priceWithComma
import com.example.carfaxassignmenmt.common.UnitConverter.numberWithK
import com.example.carfaxassignmenmt.ui.common.CommonComposeUi
import com.example.carfaxassignmenmt.ui.phonedialer.PhoneDialer
import com.example.carfaxassignmenmt.ui.theme.Blue_Primary
import com.example.data.model.ApiResult
import com.example.domain.models.CarListItem

/**
 * Created by Sagar Pujari on 02/10/22.
 */
class CarListMainUi {

    companion object {
        private const val TAG = "CarListMainUi"
    }

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
    fun CarListRowCard(carListItem: CarListItem, callPhone: MutableState<Boolean> =  remember {mutableStateOf(false)}, onClick: () -> Unit){
        if(callPhone.value){
            PhoneDialer().CallPhoneNumber(carListItem.phone){ dialogOpen ->
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
                    .padding(5.dp).
                    clickable {
                        onClick()
                    }
            ) {
                Column {
                    Image(
                        painter =  rememberAsyncImagePainter(carListItem.image),
                        "Car pic",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        contentScale = ContentScale.Crop
                    )
                    Column(Modifier.padding(8.dp)) {
                        val carPrimaryDetail =
                            "${carListItem.year} ${carListItem.make} ${carListItem.model} ${carListItem.trim}"
                        Text(
                            text = carPrimaryDetail,
                            Modifier.fillMaxWidth(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Row(modifier = Modifier.padding(top = 8.dp)) {
                            Text(
                                text = "$ ${priceWithComma(carListItem.currentPrice)}",
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
                                text = "${numberWithK(carListItem.mileage)} mi",
                                Modifier.wrapContentWidth(),
                                fontSize = 16.sp
                            )
                        }
                        Text(
                            text = carListItem.address,
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

    @Composable
    fun CarListView(navController: NavHostController, carListItems: List<CarListItem>){
        Column {
            Modifier.background(color = Color.LightGray)
            LazyColumn {
                items(carListItems) { carListItem ->
                    CarListRowCard(carListItem){
                        navController.navigate("${Constants.Screen.CarDetailScreen.route}/${carListItem.vin}")

                    }
                }
            }
        }
    }

    @Composable
    fun MainContent(
        navController: NavHostController,
        carListViewModel: CarListViewModel = hiltViewModel()
    ){
        Column {
            TopAppBarComponent()
            val apiResult: ApiResult<List<CarListItem>> by carListViewModel.carListApiResultFlow.collectAsStateWithLifecycle()
            when (apiResult) {
                is ApiResult.Loading -> {
                    Log.d(TAG, "Loading")
                    CommonComposeUi.SimpleCircularProgressIndicator()
                }
                is ApiResult.Error -> {
                    Log.d(TAG, "Error")
                    Toast.makeText(LocalContext.current, "Error Fetching Data", Toast.LENGTH_LONG).show()
                }
                is ApiResult.Success -> {
                    Log.d(TAG, (apiResult as ApiResult.Success).data.toString())
                    CarListView(navController, (apiResult as ApiResult.Success).data)
                }
            }
        }
        carListViewModel.getCarListFromRepository()
        Log.d(TAG, "Called getCarListFromRepository")
    }

    @Preview
    @Composable
    fun PreviewCarList() {
        val carListItem = CarListItem(
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
        val carListItems = arrayListOf(carListItem, carListItem, carListItem)
        LazyColumn {
            items(carListItems) { carListItem ->
                CarListRowCard(carListItem){

                }
            }
        }
    }

}