package com.example.carfaxassignmenmt

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.carfaxassignmenmt.data.model.local.CarListResponse
import com.example.carfaxassignmenmt.data.repository.CarListRepository
import com.example.carfaxassignmenmt.ui.carlist.CarListViewModel
import com.example.carfaxassignmenmt.ui.theme.CarFaxAssignmenmtTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private val carListViewModel: CarListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                carListViewModel.carListResponseStateFlow.collect{
                    when(it){
                        is CarListResponse.Loading -> Log.d(TAG, "Loading")
                        is CarListResponse.Error -> Log.d(TAG, "Error")
                        is CarListResponse.Success -> Log.d(TAG, it.carListItems.toString())
                    }
                }
            }
        }
        carListViewModel.getCarListFromRepository()
        setContent {
            CarFaxAssignmenmtTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CarFaxAssignmenmtTheme {
        Greeting("Android")
    }
}