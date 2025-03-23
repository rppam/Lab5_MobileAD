package com.example.lab5.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lab5.data.airport.Airport
import com.example.lab5.data.airport.AirportDao
import com.example.lab5.data.favorite.Favorite
import com.example.lab5.data.favorite.FavoriteDao
import java.io.File

@Database(entities = [Airport::class, Favorite::class], version = 1)
abstract class FlightSearchDatabase: RoomDatabase() {
    abstract fun airportDao(): AirportDao
    abstract fun favoriteDao(): FavoriteDao
    companion object {
        @Volatile
        private var Instance: FlightSearchDatabase? = null

        fun getDatabase(context: Context): FlightSearchDatabase {
            return synchronized(this) {
                Instance ?:
                Room
                    .databaseBuilder(context.applicationContext, FlightSearchDatabase::class.java, "Flight_search.db")
                    .createFromAsset("flight_search.db")
                    //.fallbackToDestructiveMigration()
                    .build()
                    .also {Instance = it}
            }
        }
    }
}