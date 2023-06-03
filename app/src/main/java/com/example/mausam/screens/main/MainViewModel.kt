package com.example.mausam.screens.main

import androidx.lifecycle.ViewModel
import com.example.mausam.data.DataOrException
import com.example.mausam.model.Weather
import com.example.mausam.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor
    (private val repository: WeatherRepository):ViewModel(){

        suspend fun getWeatherData(city: String, unit: String):
                DataOrException<Weather,Boolean,Exception>{
            return repository.getWeatherApi(cityQuery = city,unit = unit)
        }
}