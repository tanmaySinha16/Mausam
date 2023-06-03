package com.example.mausam.repository

import android.util.Log
import com.example.mausam.data.DataOrException
import com.example.mausam.model.Weather
import com.example.mausam.network.WeatherApi
import java.lang.Exception
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val weatherApi: WeatherApi ) {

    suspend fun getWeatherApi(
        cityQuery: String,
        unit: String
    ): DataOrException<Weather, Boolean, Exception> {
        val response = try {
            weatherApi.getWeather(query = cityQuery, units = unit)
        }
        catch (e:Exception)
        {
            Log.d("Weather","getWeather : $e")
            return DataOrException(e = e)
        }
        Log.d("Weather", "getWeatherApi: $response")
        return DataOrException(data = response)
    }

}