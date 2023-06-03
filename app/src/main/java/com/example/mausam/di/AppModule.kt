package com.example.mausam.di

import android.content.Context
import androidx.room.Room
import com.example.mausam.data.WeatherDao
import com.example.mausam.data.WeatherDatabase
import com.example.mausam.network.WeatherApi
import com.example.mausam.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesOpenWeatherApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }


    @Provides
    @Singleton
    fun providesWeatherDao(
        weatherDatabase: WeatherDatabase
    ): WeatherDao = weatherDatabase.weatherDao()


    @Provides
    @Singleton
    fun providesWeatherDatabase(
      @ApplicationContext context: Context
    ): WeatherDatabase = Room.databaseBuilder(
        context,
        WeatherDatabase::class.java,
        "weather_db"
    ).build()


}