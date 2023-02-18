package com.example.carfaxassignmenmt

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by sagar on 4/8/17.
 */
@HiltAndroidApp
class CarListApplication : Application() {

    companion object {
        operator fun get(context: Context?): CarListApplication {
            return context?.applicationContext as CarListApplication
        }
    }

}