package com.example.data.mappers

/**
 * @author Sagar Pujari
 *
 * Basic mapper implementation for converting a type [T] to another [O].
 */
abstract class BasicMapper<in T, out O> : Mapper<T, O> {
    override fun map(items: List<T>): List<O> {
        val r = mutableListOf<O>()
        for (item in items) r.add(map(item))
        return r
    }
}