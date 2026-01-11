package com.example.carfaxassignmenmt.common

/**
 * Created by Sagar Pujari on 18/02/23.
 */

    sealed class Screen(val route: String) {
        object CarList : Screen("car_list_screen"){
             val graph = route + "_graph"
        }
        object CarDetail : Screen("car_detail_screen"){
            const val carListItemId = "car_list_item_id"
        }

        fun withArgs(vararg args: String): String {
            return buildString {
                append(route)
                args.forEach{ arg ->
                    append("/$arg")
                }
            }
        }

        // build and setup route format (in navigation graph)
        fun withArgsFormat(vararg args: String) : String {
            return buildString {
                append(route)
                args.forEach{ arg ->
                    append("/{$arg}")
                }
            }
        }
    }



