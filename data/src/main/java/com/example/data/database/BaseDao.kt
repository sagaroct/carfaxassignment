package com.example.data.database

import androidx.room.*

/***
 * @author Sagar Pujari
 *
 * Base Dao class which handles core operations like insert or update with item(s)[T].
 */
@Dao
interface BaseDao<T> {
    /**
     * Insert an object in the database.
     *
     * @param item the object to be inserted.
     * @return The SQLite row id
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: T): Long

    /**
     * Insert an array of objects in the database.
     *
     * @param items the objects to be inserted.
     * @return The SQLite row ids
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<T>?): List<Long>

	/**
	 * Insert or update an object in the database.
	 *
	 * @param item the object to be inserted or updated.
	 */
	@Upsert
	suspend fun upsert(item: T)

	/**
	 * Insert or update an array of objects in the database.
	 *
	 * @param items the objects to be inserted or updated.
	 */
	@Upsert
	suspend fun upsert(items: List<T>)

    /**
     * Delete an object from the database
     *
     * @param item the object to be deleted
     */
    @Delete
    suspend fun delete(item: T)

}