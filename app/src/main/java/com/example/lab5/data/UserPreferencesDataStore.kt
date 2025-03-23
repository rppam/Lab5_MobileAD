package com.example.lab5.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.lab5.Screens
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {
    private companion object {
        val QUERY = stringPreferencesKey("query")
        val ROUTE = stringPreferencesKey("route")
    }

    suspend fun setQueryPreference(query: String) {
        dataStore.edit { preferences ->
            preferences[QUERY] = query
        }
    }

    suspend fun setRoutePreference(route: String) {
        dataStore.edit { preferences ->
            preferences[ROUTE] = route
        }
    }

    val query: Flow<Pair<String,String>> = dataStore.data.map { preferences ->
        Pair<String,String>(preferences[QUERY] ?: "", preferences[ROUTE] ?: Screens.Favorites.name)
    }
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "query_preferences"
)