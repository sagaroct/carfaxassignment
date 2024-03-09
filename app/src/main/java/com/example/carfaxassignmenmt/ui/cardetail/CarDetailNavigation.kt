package com.example.carfaxassignmenmt.ui.cardetail

import androidx.navigation.NavController
import com.example.carfaxassignmenmt.navigation.Screen

object CarDetail : Screen("car_detail_screen") {
	const val carListItemId = "car_list_item_id"
}

fun NavController.navigateToCarDetail(id: String) {
	this.navigate(CarDetail.withArgs(id))
}

