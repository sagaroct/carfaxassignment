package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.BuildConfig
import com.example.data.feature.vehicle.network.VehicleApiService
import com.example.data.common.Constants
import com.example.data.database.VehicleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

	@Singleton
	@Provides
	fun provideRetrofit(): Retrofit {
		val builder: OkHttpClient.Builder = OkHttpClient.Builder()
		if (BuildConfig.DEBUG) {
			val logging = HttpLoggingInterceptor()
			logging.setLevel(HttpLoggingInterceptor.Level.BODY)
			builder.addInterceptor(logging)
		}
		val okHttpClient: OkHttpClient = builder.build()
		return Retrofit.Builder()
			.baseUrl(Constants.RestConstants.BASE_URL)
			.client(okHttpClient)
			.addConverterFactory(GsonConverterFactory.create())
			.build()
	}

	@Singleton
	@Provides
	fun providesCarListingApiService(retrofit: Retrofit): VehicleApiService {
		return retrofit.create(VehicleApiService::class.java)
	}

  @Provides
  @Singleton
  fun provideVehicleDatabase(@ApplicationContext appContext: Context): VehicleDatabase {
	  return Room.databaseBuilder(
	    appContext,
	    VehicleDatabase::class.java,
	    "vehicle_database"
    )
	    .fallbackToDestructiveMigration()
	    .build()
  }

}