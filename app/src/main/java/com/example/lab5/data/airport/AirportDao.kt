package com.example.lab5.data.airport

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface AirportDao {
    @Query("Select * from airport where iata_code is not :departureAirportCode order by passengers desc")
    fun getArrivalAirports(departureAirportCode: String): Flow<List<Airport>>

    @Query("Select * from airport where lower(iata_code) like '%' || lower(:search) || '%' or " +
            "lower(name) like '%' || lower(:search) || '%'")
    fun getSearchedAirports(search: String): Flow<List<Airport>>

    @Query("Select name from airport where iata_code == :iata_code")
    fun getAirportName(iata_code: String): Flow<String>
}