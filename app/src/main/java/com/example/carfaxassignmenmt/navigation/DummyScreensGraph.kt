/*
package com.example.carfaxassignmenmt.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.carfaxassignmenmt.navigation.Screen
import com.example.carfaxassignmenmt.ui.dummyscreens.DummyTextA
import com.example.carfaxassignmenmt.ui.dummyscreens.DummyTextB

*/
/**
 * Navgraph ceated for car the screen(ui module) of carlist and detail.
 * In similar manner for different ui modules we can create a separate file for its nav graph.
 *//*

fun NavGraphBuilder.dummyScreensGraph(
	navController: NavController
) {
	navigation(
		startDestination = Screen.DummyScreenA.route, //This route is from where our very first screen route name is mentioned.
		route = Screen.DummyScreenA.graph, //This route is the unique route created for this navigation.
	) {
		composable(Screen.DummyScreenA.route) {
			DummyTextA(onNavigationToDummyScreenB = {
				navController.navigate(Screen.DummyScreenB.route)
			})
		}
		composable(
			route = Screen.DummyScreenB.route) {
			DummyTextB()
			}
		}
	}
*/
