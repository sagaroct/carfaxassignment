package com.example.carfaxassignmenmt.ui.cardetail

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.carfaxassignmenmt.data.model.local.CarListItem
import com.example.carfaxassignmenmt.ui.carlist.CarListMainUi
import com.example.carfaxassignmenmt.ui.theme.CarFaxAssignmenmtTheme

class CarDetailActivity : ComponentActivity() {

    private val carListItem: CarListItem? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(CAR_LIST_ITEM, CarListItem::class.java) as CarListItem
        } else {
            intent?.getParcelableExtra(CAR_LIST_ITEM) as CarListItem?

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CarFaxAssignmenmtTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    carListItem?.let {
                        CarDetailUi().CarDetail(it)
                    }
                }
            }
        }
    }

    companion object {
        private const val CAR_LIST_ITEM = "CAR_LIST_ITEM"

        fun newIntent(context: Context, carListItem: CarListItem) =
            Intent(context, CarDetailActivity::class.java).apply {
                putExtra(CAR_LIST_ITEM, carListItem)
            }
    }
}