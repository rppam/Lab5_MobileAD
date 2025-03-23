package com.example.lab5.data.airport

import kotlinx.coroutines.flow.Flow


class OfflineAirportRepository(
    private val airportDao: AirportDao
): AirportRepository {
    override fun getArrivalAirports(departureAirportCode: String): Flow<List<Airport>> =
        airportDao.getArrivalAirports(departureAirportCode)

    override fun getSearchedAirports(search: String): Flow<List<Airport>> =
        airportDao.getSearchedAirports(search)

    override fun getAirportName(iata_code: String): Flow<String> =
        airportDao.getAirportName(iata_code)
}