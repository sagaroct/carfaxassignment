package com.example.carfaxassignmenmt.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.carfaxassignmenmt.R
import com.example.carfaxassignmenmt.ui.theme.Blue_Primary

@Composable
fun SimpleCircularProgressIndicator() {
	Column(
		modifier = Modifier.fillMaxSize(),
		verticalArrangement = Arrangement.Center,
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		CircularProgressIndicator(color = Blue_Primary)
	}
}

@Composable
fun TopAppBarComponent(
	title: String,
	showBackButton: Boolean = true,
	onBackClick: () -> Unit = {}
) {
	TopAppBar(
		modifier = Modifier
			.fillMaxWidth(),
		title = { Text(title) },
		navigationIcon = if (showBackButton) {
			{
				IconButton(onClick = onBackClick) {
					Icon(
						imageVector = Icons.Filled.ArrowBack,
						contentDescription = stringResource(id = R.string.back)
					)
				}
			}
		} else {
			null
		}
	)
}

@Preview
@Composable
fun TopAppBarComponentPreview() {
	TopAppBarComponent(title = "Vehicles", showBackButton = true)
}