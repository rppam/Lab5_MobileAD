package com.example.lab5.data

import android.content.Context
import com.example.lab5.data.airport.AirportRepository
import com.example.lab5.data.airport.OfflineAirportRepository
import com.example.lab5.data.favorite.FavoriteRepository
import com.example.lab5.data.favorite.OfflineFavoriteRepository

interface AppContainer {
    val airportRepository: AirportRepository
    val favoriteRepository: FavoriteRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val airportRepository: AirportRepository by lazy {
        OfflineAirportRepository(FlightSearchDatabase.getDatabase(context).airportDao())
    }
    override val favoriteRepository: FavoriteRepository by lazy {
        OfflineFavoriteRepository(FlightSearchDatabase.getDatabase(context).favoriteDao())
    }
}