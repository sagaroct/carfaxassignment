package com.example.carfaxassignmenmt.app

import android.app.Application
import android.content.Context
import coil.ImageLoader
import coil.ImageLoaderFactory
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by sagar on 4/8/17.
 */
@HiltAndroidApp
class CarListApplication : Application(), ImageLoaderFactory {

    companion object {
        operator fun get(context: Context?): CarListApplication {
            return context?.applicationContext as CarListApplication
        }
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .respectCacheHeaders(false)
            .build()
    }

}