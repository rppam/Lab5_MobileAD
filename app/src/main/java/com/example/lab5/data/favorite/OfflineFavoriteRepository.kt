package com.example.lab5.data.favorite

import kotlinx.coroutines.flow.Flow


class OfflineFavoriteRepository(
    private val favoriteDao: FavoriteDao
): FavoriteRepository {
    override suspend fun insert(favorite: Favorite) =
        favoriteDao.insert(favorite)

    override suspend fun update(favorite: Favorite) =
        favoriteDao.update(favorite)

    override fun selectAll(): Flow<List<Favorite>> =
        favoriteDao.selectAll()

    override fun find(departureAirportCode: String, arrivalAirportCode: String): Flow<Int> =
        favoriteDao.find(departureAirportCode, arrivalAirportCode)
}