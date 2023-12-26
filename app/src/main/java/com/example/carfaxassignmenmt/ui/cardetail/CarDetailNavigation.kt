package com.example.carfaxassignmenmt.ui.cardetail

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.carfaxassignmenmt.navigation.Screen

object CarDetail : Screen("car_detail_screen") {
	const val carListItemId = "car_list_item_id"
}

fun NavController.navigateToCarDetail(id: String) {
	this.navigate(CarDetail.withArgs(id))
}

fun NavGraphBuilder.carDetailScreenGraph() {
	composable(
		route = CarDetail.withArgsFormat(CarDetail.carListItemId),
		arguments = listOf(
			navArgument(CarDetail.carListItemId) {
				type = NavType.StringType
				nullable = false
			}
		)) { navBackStackEntry ->
		val carListItemId = navBackStackEntry.arguments?.getString(CarDetail.carListItemId)
		carListItemId?.let {
			CarDetailScreen().MainContent(carListItemId = it)
		}
	}
}