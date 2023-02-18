package com.example.carfaxassignmenmt.data.mappers

/**
 * @author Sagar Pujari
 *
 * interface for converting a type T to another O.
 */
interface Mapper<in T, out O> {
    fun map(item: T): O

    //    fun mapNullable(item: T?): O?
    fun map(items: List<T>): List<O>
}