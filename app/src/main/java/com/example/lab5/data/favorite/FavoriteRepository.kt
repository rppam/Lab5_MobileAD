package com.example.lab5.data.favorite

import kotlinx.coroutines.flow.Flow


interface FavoriteRepository {
    suspend fun insert(favorite: Favorite)
    suspend fun update(favorite: Favorite)
    fun selectAll(): Flow<List<Favorite>>
    fun find(departureAirportCode: String, arrivalAirportCode: String): Flow<Int>
}