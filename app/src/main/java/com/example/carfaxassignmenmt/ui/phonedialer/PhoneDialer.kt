package com.example.carfaxassignmenmt.ui.phonedialer

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

/**
 * Created by Sagar Pujari on 20/02/23.
 */
class PhoneDialer {

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun CallPhoneNumber(phoneNum: String, onDismiss : (dialogOpen: Boolean) -> Unit) {
        // Camera permission state
        val callPermissionState = rememberPermissionState(
            android.Manifest.permission.CALL_PHONE
        )
        when (callPermissionState.status) {
            // If the camera permission is granted, then show screen with the feature enabled
            PermissionStatus.Granted -> {
                callNumber(LocalContext.current, phoneNum)
                onDismiss.invoke(false)
            }
            is PermissionStatus.Denied -> {
                val textToShow = if ((callPermissionState.status as PermissionStatus.Denied).shouldShowRationale) {
                    // If the user has denied the permission but the rationale can be shown,
                    // then gently explain why the app requires this permission
                    "Please grant Call permission."
                } else {
                    // If it's the first time the user lands on this feature, or the user
                    // doesn't want to be asked again for this permission, explain that the
                    // permission is required
                    "Call permission required to place a Call " +
                            "Please grant the permission"
                }
                AlertDialog(
                    title = {
                        Text(text = "Grant Permission")
                    },
                    text = {
                        Text(textToShow)
                    },
                    onDismissRequest = {
                        onDismiss.invoke(false)
                    },
                    buttons = {
                        Button(onClick = { callPermissionState.launchPermissionRequest() },
                            Modifier.padding(start = 10.dp, end = 10.dp).fillMaxWidth()) {
                            Text("Request Phone Call Permission")
                        }
                    }
                )
            }
        }
    }

    private fun callNumber(context: Context, phoneNum: String){
        Log.d("Car", "phoneNum: $phoneNum")
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNum"))
        ContextCompat.startActivity(context, intent, null)
    }
}