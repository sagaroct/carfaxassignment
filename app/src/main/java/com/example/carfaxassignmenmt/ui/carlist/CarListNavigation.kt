package com.example.carfaxassignmenmt.ui.carlist

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.carfaxassignmenmt.navigation.Screen
import com.example.carfaxassignmenmt.ui.cardetail.CarDetail
import com.example.carfaxassignmenmt.ui.cardetail.CarDetailScreen
import com.example.carfaxassignmenmt.ui.cardetail.navigateToCarDetail

object CarList : Screen("car_list_screen")

fun NavGraphBuilder.carListScreenGraph(navController: NavController) {
	navigation(startDestination = CarList.route, route = CarList.graph){
		composable(route = CarList.route) {
			val carListViewModel: CarListViewModel = hiltViewModel()
			CarListScreen().MainContent(
				onNavigationToDetailScreen = { id ->
					navController.navigateToCarDetail(id)
				}, onNavigationToDummyScreen = {
//				navController.navigate(Screen.DummyScreenA.route)
				}, carListViewModel )
		}
		composable(
			route = CarDetail.withArgsFormat(CarDetail.carListItemId),
			arguments = listOf(
				navArgument(CarDetail.carListItemId) {
					type = NavType.StringType
					nullable = false
				}
			)) { navBackStackEntry ->
			val carListViewModel: CarListViewModel = hiltViewModel()
			val carListItemId = navBackStackEntry.arguments?.getString(CarDetail.carListItemId)
			carListItemId?.let {
				CarDetailScreen().MainContent(carListItemId = it, carListViewModel)
			}
		}
	}
}

