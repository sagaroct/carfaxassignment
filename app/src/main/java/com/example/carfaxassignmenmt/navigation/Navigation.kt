package com.example.carfaxassignmenmt.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.carfaxassignmenmt.common.Screen

/**
 * Created by Sagar Pujari on 20/02/23.
 */

@Composable
fun Navigation() {
	val navController = rememberNavController()
	NavHost(navController = navController, startDestination = Screen.CarList.graph) {
		carListAndDetailGraph(navController)
	}
}

