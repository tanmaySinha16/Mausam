package com.example.mausam.repository

import com.example.mausam.data.WeatherDao
import com.example.mausam.model.Favourite
import com.example.mausam.model.Unit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository@Inject constructor(
    private val weatherDao: WeatherDao
) {
    fun getFavourites(): Flow<List<Favourite>> = weatherDao.getFavourites()
    suspend fun insertFavourite(favourite:Favourite) = weatherDao.insertFavourite(favourite = favourite)
    suspend fun deleteFavourite(favourite: Favourite) = weatherDao.deleteFavourite(favourite = favourite)
    suspend fun updateFavourite(favourite: Favourite) = weatherDao.updateFavourite(favourite = favourite)
    suspend fun deleteAllFavourite(favourite: Favourite) = weatherDao.deleteAllFavourites()

    fun getUnits() = weatherDao.getUnits()
    suspend fun insertUnit(unit:Unit) = weatherDao.insertUnit(unit)
    suspend fun updateUnit(unit:Unit) = weatherDao.updateUnit(unit)
    suspend fun deleteUnit(unit:Unit) = weatherDao.deleteUnit(unit = unit)
    suspend fun deleteAllUnits() = weatherDao.deleteAllUnits()
}