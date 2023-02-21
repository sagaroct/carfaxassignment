package com.example.carfaxassignmenmt.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.carfaxassignmenmt.ui.theme.Blue_Primary


/**
 * Created by Sagar Pujari on 21/02/23.
 */
object CommonComposeUi {

    @Composable
    fun SimpleCircularProgressIndicator() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center ,
             horizontalAlignment  = Alignment.CenterHorizontally) {
            CircularProgressIndicator(color = Blue_Primary)
        }
    }

}