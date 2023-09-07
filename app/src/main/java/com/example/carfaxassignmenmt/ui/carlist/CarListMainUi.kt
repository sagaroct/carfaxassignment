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
import coil.compose.rememberAsyncImagePainter
import com.example.carfaxassignmenmt.R
import com.example.carfaxassignmenmt.common.UnitConverter.priceWithComma
import com.example.carfaxassignmenmt.common.UnitConverter.numberWithK
import com.example.carfaxassignmenmt.ui.common.CommonComposeUi
import com.example.carfaxassignmenmt.ui.phonedialer.PhoneDialer
import com.example.carfaxassignmenmt.ui.theme.Blue_Primary
import com.example.domain.models.ApiResult
import com.example.domain.models.CarItem

/**
 * Created by Sagar Pujari on 02/10/22.
 */
class CarListMainUi {

    companion object {
        private const val TAG = "CarListMainUi"
    }

    @Composable
    fun TopAppBarComponent(onNavigationToDummyScreen: () -> Unit) {
        TopAppBar(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onNavigationToDummyScreen.invoke()
                },
            title = { Text(stringResource(id = R.string.app_name)) }
        )
    }

    @SuppressLint("UnrememberedMutableState")
    @Composable
    fun CarListRowCard(carItem: CarItem, callPhone: MutableState<Boolean> =  remember {mutableStateOf(false)}, onClick: () -> Unit){
        if(callPhone.value){
            PhoneDialer().CallPhoneNumber(carItem.phone){ dialogOpen ->
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
                        painter =  rememberAsyncImagePainter(carItem.image),
                        "Car pic",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        contentScale = ContentScale.Crop
                    )
                    Column(Modifier.padding(8.dp)) {
                        val carPrimaryDetail =
                            "${carItem.year} ${carItem.make} ${carItem.model} ${carItem.trim}"
                        Text(
                            text = carPrimaryDetail,
                            Modifier.fillMaxWidth(),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Row(modifier = Modifier.padding(top = 8.dp)) {
                            Text(
                                text = "$ ${priceWithComma(carItem.currentPrice)}",
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
                                text = "${numberWithK(carItem.mileage)} mi",
                                Modifier.wrapContentWidth(),
                                fontSize = 16.sp
                            )
                        }
                        Text(
                            text = carItem.address,
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
    fun CarListView(carItems: List<CarItem>, onNavigationToDetailScreen: (id: String) -> Unit){
        Column {
            Modifier.background(color = Color.LightGray)
            LazyColumn {
                items(carItems) { carListItem ->
                    CarListRowCard(carListItem){
                        onNavigationToDetailScreen.invoke(carListItem.vin)/*.navigate("${Screen.CarDetail.route}/${carListItem.vin}"*/
                    }
                }
            }
        }
    }

    @Composable
    fun MainContent(
        onNavigationToDetailScreen: (id: String) -> Unit,
        onNavigationToDummyScreen: () -> Unit,
        carListViewModel: CarListViewModel = hiltViewModel()
    ){
        Column {
            TopAppBarComponent(onNavigationToDummyScreen)
            val apiResult: ApiResult<List<CarItem>> by carListViewModel.carListApiResultFlow.collectAsStateWithLifecycle()
            when (apiResult) {
                is ApiResult.Loading -> {
                    Log.d(TAG, "Loading")
                    CommonComposeUi.SimpleCircularProgressIndicator()
                }
                is ApiResult.Error -> {
                    val errorMsg = (apiResult as ApiResult.Error).error.message ?: "Error Fetching Data"
                    Log.d(TAG, errorMsg)
                    Toast.makeText(LocalContext.current, errorMsg, Toast.LENGTH_LONG).show()
                }
                is ApiResult.Success -> {
                    Log.d(TAG, (apiResult as ApiResult.Success).data.toString())
                    CarListView((apiResult as ApiResult.Success).data, onNavigationToDetailScreen)
                }
            }
        }
        carListViewModel.getCarListFromRepository()
        Log.d(TAG, "Called getCarListFromRepository")
    }

    @Preview
    @Composable
    fun PreviewCarList() {
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
        val carListItems = arrayListOf(carItem, carItem, carItem)
        LazyColumn {
            items(carListItems) { carListItem ->
                CarListRowCard(carListItem){

                }
            }
        }
    }

}