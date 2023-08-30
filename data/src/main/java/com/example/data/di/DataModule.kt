package com.example.data.di

import android.content.Context
import com.example.data.database.CarListingDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

  @Provides
  @Singleton
  fun provideAppDatabase(@ApplicationContext appContext: Context): CarListingDatabase {
    return CarListingDatabase.get(appContext)
  }

  @Provides
  fun provideDispatcher() = Dispatchers.IO //TODO: Not sure if this is the right place to provide dispatcher.

}