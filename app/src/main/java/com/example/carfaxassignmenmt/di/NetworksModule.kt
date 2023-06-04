package com.example.carfaxassignmenmt.di

import com.example.carfaxassignmenmt.BuildConfig
import com.example.carfaxassignmenmt.common.Constants
import com.example.carfaxassignmenmt.data.network.CarListingApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworksModule {

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
  fun providesMovieApiService(retrofit: Retrofit): CarListingApiService {
    return retrofit.create(CarListingApiService::class.java)
  }

}