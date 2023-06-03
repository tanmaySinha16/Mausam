package com.example.mausam.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mausam.model.Favourite
import com.example.mausam.model.Unit

@Database(entities = [Favourite::class, Unit::class], version = 2, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao():WeatherDao
}