package com.example.lab5.data

import com.example.lab5.Screens

data class AirportsUIState (
    var query: String = "", // Поисковый запрос
    var route: String = Screens.Favorites.name // Выбранный экран
)