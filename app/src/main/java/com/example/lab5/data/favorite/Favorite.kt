package com.example.lab5.data.favorite

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class Favorite (
    @PrimaryKey
    val id: Int,
    val departure_code: String,
    val destination_code: String
)