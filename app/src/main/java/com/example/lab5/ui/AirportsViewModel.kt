package com.example.lab5.ui

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lab5.data.AirportsUIState
import com.example.lab5.data.AppContainer
import com.example.lab5.data.AppDataContainer
import com.example.lab5.data.FlightSearchDatabase
import com.example.lab5.data.RouteInfo
import com.example.lab5.data.UserPreferencesRepository
import com.example.lab5.data.airport.AirportRepository
import com.example.lab5.data.airport.OfflineAirportRepository
import com.example.lab5.data.dataStore
import com.example.lab5.data.favorite.Favorite
import com.example.lab5.data.favorite.FavoriteRepository
import com.example.lab5.data.favorite.OfflineFavoriteRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AirportsViewModel(application: Application) : ViewModel() {
    private val _uiState = MutableStateFlow(AirportsUIState())
    val uiState : StateFlow<AirportsUIState> = _uiState.asStateFlow()

    private var airportRepository: AirportRepository
    private var favoriteRepository: FavoriteRepository
    private val userPreferencesRepository: UserPreferencesRepository

    init {
        val db = FlightSearchDatabase.getDatabase(application)
        airportRepository = OfflineAirportRepository(db.airportDao())
        favoriteRepository = OfflineFavoriteRepository(db.favoriteDao())
        userPreferencesRepository = UserPreferencesRepository(application.applicationContext.dataStore)
    }

    fun SetQuery(query: String) {
        _uiState.update {
            it.copy(query = query)
        }
    }

    fun SetRoute(route: String) {
        _uiState.update {
            it.copy(route = route)
        }
    }

    //region datastore


//    suspend fun SetQuery(query: String) {
//        userPreferencesRepository.setQueryPreference(query)
//    }
//
//    suspend fun SetRoute(route: String) {
//        userPreferencesRepository.setRoutePrefence(route)
//    }

    val initUiState : StateFlow<AirportsUIState> =
        userPreferencesRepository.query.map { pair ->
            AirportsUIState(query = pair.first, route = pair.second)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AirportsUIState()
        )

    @Composable
    fun Load() {
        val value = initUiState.collectAsState().value
        SetQuery(value.query)
        SetRoute(value.route)
    }


    suspend fun Save() {
        userPreferencesRepository.setQueryPreference(_uiState.value.query)
        userPreferencesRepository.setRoutePreference(_uiState.value.route)
    }
    //endregion



    @Composable
    fun GetFavorites(): List<RouteInfo> {
        val result: MutableList<RouteInfo> = mutableListOf()
        val data = favoriteRepository.selectAll().collectAsState(listOf()).value
        for(item in data) {
            result.add(RouteInfo(
                departureAirportCode = item.departure_code,
                arrivalAirportCode = item.destination_code,
                isFavorite = true
            ))
        }
        return result.toList()
    }

    @Composable
    fun GetSearchAirports(): List<Pair<String,String>> {
        val result: MutableList<Pair<String,String>> = mutableListOf()
        val data = airportRepository.getSearchedAirports(uiState.collectAsState().value.query)
            .collectAsState(listOf()).value
        for(item in data) {
            result.add(Pair(item.iata_code, item.name))
        }
        return result.toList()
    }

    @Composable
    fun GetArrivalAirports(): List<RouteInfo> {
        val result: MutableList<RouteInfo> = mutableListOf()
        val iata_code = uiState.collectAsState().value.query
        val data = airportRepository.getArrivalAirports(iata_code).collectAsState(listOf()).value
        for(item in data) {
            result.add(RouteInfo(
                departureAirportCode = iata_code,
                departureAirportName = airportRepository.getAirportName(iata_code).collectAsState("").value,
                arrivalAirportCode = item.iata_code,
                arrivalAirportName = item.name,
                isFavorite = favoriteRepository.find(iata_code, item.iata_code).collectAsState(0).value > 0)
            )
        }
        return result
    }

    suspend fun addFavorite(departureAirportCode: String, arrivalAirportCode: String) {
        val favorite = Favorite(
            id = (departureAirportCode + arrivalAirportCode).hashCode(),
            departure_code = departureAirportCode,
            destination_code = arrivalAirportCode
        )

        favoriteRepository.insert(favorite)
    }

    suspend fun deleteFavorite(departureAirportCode: String, arrivalAirportCode: String) {
        val favorite = Favorite(
            id = (departureAirportCode + arrivalAirportCode).hashCode(),
            departure_code = departureAirportCode,
            destination_code = arrivalAirportCode
        )
        favoriteRepository.update(favorite)
    }
}