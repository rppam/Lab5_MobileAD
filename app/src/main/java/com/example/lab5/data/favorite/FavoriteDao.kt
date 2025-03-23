package com.example.lab5.data.favorite

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert
    suspend fun insert(favorite: Favorite)

    @Delete
    suspend fun update(favorite: Favorite)

    @Query("Select * from favorite")
    fun selectAll(): Flow<List<Favorite>>

    @Query("Select count(*) from favorite where departure_code = :departureAirportCode and " +
            "destination_code = :arrivalAirportCode")
    fun find(departureAirportCode: String, arrivalAirportCode: String): Flow<Int>
}