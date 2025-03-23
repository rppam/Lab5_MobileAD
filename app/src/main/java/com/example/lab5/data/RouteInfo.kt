package com.example.lab5.data

data class RouteInfo (
    val departureAirportCode: String,
    val arrivalAirportCode: String,
    val departureAirportName: String? = null,
    val arrivalAirportName: String? = null,
    var isFavorite: Boolean = false
)