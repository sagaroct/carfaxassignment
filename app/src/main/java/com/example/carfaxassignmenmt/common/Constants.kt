package com.example.carfaxassignmenmt.common

/**
 * Created by Sagar Pujari on 18/02/23.
 */
object Constants {

    object Table {
        const val ROOM_CAR_LIST_ITEM = "ROOM_CAR_LIST_ITEM"
    }

    object RestConstants {
        const val BASE_URL = "https://carfax-for-consumers.firebaseio.com/"

    }

    sealed class Screen(val route: String) {
        object CarListScreen : Screen("car_list_screen")
        object CarDetailScreen : Screen("car_detail_screen")

        object CarDetailArgs {
            const val carListItemId = "car_list_item_id"
        }
    }
}