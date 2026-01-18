package com.example.carfaxassignmenmt.navigation

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.carfaxassignmenmt.common.Screen

/**
 * Created by Sagar Pujari on 20/02/23.
 */

@Composable
fun Navigation() {
	val navController = rememberNavController()
	val snackbarHostState = remember { SnackbarHostState() }
	NavHost(navController = navController, startDestination = Screen.CarList.graph) {
		vehicleListAndDetailGraph(navController, snackbarHostState)
	}
}

