package com.example.carfaxassignmenmt.data.database

import androidx.room.*

/***
 * @author Sagar Pujari
 *
 * Base Dao class which handles core operations like insert or update with item(s)[T].
 */
@Dao
abstract class BaseDao<T> {
    /**
     * Insert an object in the database.
     *
     * @param item the object to be inserted.
     * @return The SQLite row id
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(item: T): Long

    /**
     * Insert an array of objects in the database.
     *
     * @param items the objects to be inserted.
     * @return The SQLite row ids
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insert(items: List<T>?): List<Long>

    /**
     * Update an object from the database.
     *
     * @param item the object to be updated
     */
    @Update
    abstract suspend fun update(item: T)

    /**
     * Update an array of objects from the database.
     *
     * @param items the object to be updated
     */
    @Update
    abstract suspend fun update(items: List<T>?)

    /**
     * Delete an object from the database
     *
     * @param item the object to be deleted
     */
    @Delete
    abstract suspend fun delete(item: T)

    @Transaction
    open suspend fun insertOrUpdate(item: T) {
        val id = insert(item)
        if (id == -1L) {
            update(item)
        }
    }

    @Transaction
    open suspend fun insertOrUpdate(items: List<T>) {
        val insertResult = insert(items)
        val updateList: MutableList<T> = ArrayList()
        for (i in insertResult.indices) {
            if (insertResult[i] == -1L) {
                updateList.add(items[i])
            }
        }
        if (updateList.isNotEmpty()) {
            update(updateList)
        }
    }
}