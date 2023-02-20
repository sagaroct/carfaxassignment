package com.example.carfaxassignmenmt.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.carfaxassignmenmt.common.Constants
import com.example.carfaxassignmenmt.ui.cardetail.CarDetailUi
import com.example.carfaxassignmenmt.ui.carlist.CarListMainUi

/**
 * Created by Sagar Pujari on 20/02/23.
 */

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Constants.Screen.CarListScreen.route){
        composable(route = Constants.Screen.CarListScreen.route){
            CarListMainUi().MainContent(navController = navController)
        }
        composable(
            route = Constants.Screen.CarDetailScreen.route + "/{${Constants.Screen.CarDetailArgs.carListItemId}}",
            arguments = listOf(
            navArgument(Constants.Screen.CarDetailArgs.carListItemId){
                type = NavType.StringType
                nullable = false
            }
        )){ entry ->
            val carListItemId = entry.arguments?.getString(Constants.Screen.CarDetailArgs.carListItemId)
            carListItemId?.let {
                CarDetailUi().MainContent(carListItemId = it)
            }
        }
    }
}