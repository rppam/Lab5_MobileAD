package com.example.lab5.data.airport

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "airport")
data class Airport (
    @PrimaryKey
    val id: Int,
    val name: String,
    val iata_code: String,
    val passengers: Int
)