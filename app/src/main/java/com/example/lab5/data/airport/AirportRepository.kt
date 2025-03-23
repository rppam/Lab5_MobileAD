package com.example.lab5.data.airport

import kotlinx.coroutines.flow.Flow


interface AirportRepository {
    fun getArrivalAirports(departureAirportCode: String): Flow<List<Airport>>
    fun getSearchedAirports(search: String): Flow<List<Airport>>
    fun getAirportName(iata_code: String): Flow<String>
}