package com.example.data.database

import android.content.Context
import androidx.room.*
import com.example.data.carlist.model.db.CarListingLocalSource
import com.example.data.carlist.model.db.RoomCarListItem

/**
 * @author Sagar Pujari
 *
 * Car Listing DataBase class which registers all the entities and DAO's
 */
@Database(
    entities = [RoomCarListItem::class],
    version = 1,
    exportSchema = true
) abstract class CarListingDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var x: CarListingDatabase? = null
        private const val DATABASE_NAME = "car_listing"

        //singleton pattern
        @JvmStatic
        fun get(context: Context): CarListingDatabase {
            return x ?: synchronized(this) {
                x ?: buildDatabase(context).also { x = it }
            }
        }

        private fun buildDatabase(context: Context): CarListingDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                CarListingDatabase::class.java,
                DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }
    }

    // getters for app DAOs

    abstract fun getCarListingLocalSource(): CarListingLocalSource

}