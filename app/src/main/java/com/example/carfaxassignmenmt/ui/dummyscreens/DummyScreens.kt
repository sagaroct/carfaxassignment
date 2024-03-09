package com.example.carfaxassignmenmt.ui.dummyscreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp


@Preview
@Composable
fun PreviewDummyTextA() {
	DummyTextA(onNavigationToDummyScreenB = {

	})
	DummyTextB()
}

@Composable
fun DummyTextA(onNavigationToDummyScreenB: () -> Unit) {
	Column(
		modifier = Modifier
			.fillMaxSize()
			.background(color = Color.White)
			.clickable {
				onNavigationToDummyScreenB.invoke()
			}
	) {
		Text(
			fontSize = 32.sp,
			text = "Hello A",
			color = Color.Blue
		)
	}
}

@Composable
fun DummyTextB() {
	Column(
		modifier = Modifier
			.fillMaxSize()
			.background(color = Color.White)
	) {
		Text(
			fontSize = 32.sp,
			text = "Hello B",
			color = Color.Blue
		)
	}
}

