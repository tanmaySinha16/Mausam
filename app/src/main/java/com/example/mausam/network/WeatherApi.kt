package com.example.mausam.network

import com.example.mausam.model.Weather
import com.example.mausam.model.WeatherObject
import com.example.mausam.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {
    @GET(value = "data/2.5/forecast/daily")
    suspend fun getWeather(
        @Query("q")query:String,
        @Query("units")units:String = "metric",
        @Query("appid")appid:String = Constants.API_KEY
    ): Weather
}