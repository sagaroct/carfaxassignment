package com.example.carfaxassignmenmt.ui.carlist

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.carfaxassignmenmt.navigation.Screen
import com.example.carfaxassignmenmt.ui.cardetail.navigateToCarDetail

object CarList : Screen("car_list_screen")

fun NavGraphBuilder.carListScreenGraph(navController: NavController) {
	composable(route = CarList.route) {
		CarListScreen().MainContent(
			onNavigationToDetailScreen = { id ->
				navController.navigateToCarDetail(id)
			}, onNavigationToDummyScreen = {
//				navController.navigate(Screen.DummyScreenA.route)
			})
	}
}

