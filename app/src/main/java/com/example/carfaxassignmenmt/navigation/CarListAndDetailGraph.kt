package com.example.carfaxassignmenmt.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.carfaxassignmenmt.common.Screen
import com.example.carfaxassignmenmt.ui.cardetail.CarDetailUi
import com.example.carfaxassignmenmt.ui.carlist.CarListMainUi

/**
 * Navgraph ceated for car the screen(ui module) of carlist and detail.
 * In similar manner for different ui modules we can create a separate file for its nav graph.
 */
fun NavGraphBuilder.carListAndDetailGraph(
	navController: NavController
) {
	navigation(
		startDestination = Screen.CarList.route, //This route is from where our very first screen route name is mentioned.
		route = Screen.CarList.graph, //This route is the unique route created for this navigation.
	) {
		composable(Screen.CarList.route) {
			CarListMainUi().MainContent(onNavigationToDetailScreen = { id ->
				navController.navigate(Screen.CarDetail.withArgs(id))
			}, onNavigationToDummyScreen = {
				navController.navigate(Screen.DummyScreenA.route)
			})
		}
		composable(
			route = Screen.CarDetail.withArgsFormat(Screen.CarDetail.carListItemId),
			arguments = listOf(
				navArgument(Screen.CarDetail.carListItemId) {
					type = NavType.StringType
					nullable = false
				}
			)) { entry ->
			val carListItemId = entry.arguments?.getString(Screen.CarDetail.carListItemId)
			carListItemId?.let {
				CarDetailUi().MainContent(carListItemId = it)
			}
		}
	}

}