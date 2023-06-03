package com.example.mausam.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mausam.data.DataOrException
import com.example.mausam.model.Weather
import com.example.mausam.model.WeatherItem
import com.example.mausam.navigation.WeatherScreens
import com.example.mausam.screens.main.MainViewModel
import com.example.mausam.screens.settings.SettingsViewModel
import com.example.mausam.utils.formatDate
import com.example.mausam.utils.formatDecimals
import com.example.mausam.widgets.HumidityWindPressureRow
import com.example.mausam.widgets.SunsetSunriseRow
import com.example.mausam.widgets.WeatherAppBar
import com.example.mausam.widgets.WeatherDetailRow
import com.example.mausam.widgets.WeatherStateImage


@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    city: String?
) {
        val curCity: String = if (city!!.isBlank()) "Bengaluru" else city
        val unitFromDb = settingsViewModel.unitList.collectAsState().value
        var unit = remember {
            mutableStateOf("imperial")
        }
        var isImperial = remember {
            mutableStateOf(false)
        }
        if(!unitFromDb.isNullOrEmpty())
        {
            unit.value = unitFromDb[0].unit.split(" ")[0].lowercase()
            isImperial.value = unit.value == "imperial"
            val weatherData = produceState<DataOrException<Weather,Boolean,Exception>>(
                initialValue = DataOrException(loading = true)) {
                value = mainViewModel.getWeatherData(city = curCity, unit = unit.value)
            }.value
            if (weatherData.loading == true){
                CircularProgressIndicator()
            }else if(weatherData.data!=null)
            {
                MainScaffold(weather = weatherData.data!!,navController,isImperial = isImperial.value)
            }
        }
        else
        {
            val weatherData = produceState<DataOrException<Weather,Boolean,Exception>>(
                initialValue = DataOrException(loading = true)) {
                value = mainViewModel.getWeatherData(city = curCity, unit = "metric")
            }.value
            if (weatherData.loading == true){
                CircularProgressIndicator()
            }else if(weatherData.data!=null)
            {
                MainScaffold(weather = weatherData.data!!,navController,isImperial = isImperial.value)
            }
        }

    }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(weather: Weather, navController: NavController, isImperial: Boolean) {
    Scaffold(topBar = {
        WeatherAppBar(title = weather.city.name + ","+weather.city.country,
            navController = navController,
        elevation = 5.dp,
            onAddActionClicked = {
                navController.navigate(WeatherScreens.SearchScreen.name)
            },
        ){
            Log.d("Tag","Button Clicked")
        }
    }){
        Box(modifier = Modifier
            .padding(it)
            .fillMaxSize())
        {
            MainContent(data = weather,isImperial = isImperial)
        }
    }
}

@Composable
fun MainContent(data: Weather, isImperial: Boolean) {

    val weatherItem = data.list[0]
    val imageUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}.png"

    Column(
        Modifier
            .padding(4.dp)
            .fillMaxWidth(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally) {

        Text(
            text = formatDate( weatherItem.dt),
            style = MaterialTheme.typography.titleSmall,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(6.dp)
        )

        Surface(modifier = Modifier
            .padding(4.dp)
            .size(200.dp)
            ,shape = CircleShape,
        color = Color(0xFFFFC400)
        ){
            Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
               WeatherStateImage(imageUrl = imageUrl)
               Text(text = formatDecimals(weatherItem.temp.day)+"°" + if(isImperial) "F" else "C",
               style = MaterialTheme.typography.headlineMedium,
               fontWeight = FontWeight.ExtraBold)
               Text(text = weatherItem.weather[0].main, fontStyle = FontStyle.Italic)
            }
        }
        HumidityWindPressureRow(weather = weatherItem, isImperial = isImperial)
        Divider()
        SunsetSunriseRow(weather = weatherItem)
        Text(text = "This Week",
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(6.dp)
        )
        Surface(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
            color = Color(0xFFEEF1EF),
            shape = RoundedCornerShape(size=14.dp)
        ) {
            LazyColumn(modifier = Modifier.padding(2.dp),
                contentPadding = PaddingValues(1.dp)
            ){
                items(data.list){ item:WeatherItem ->
                    WeatherDetailRow(weather = item)
                }
            }
        }
    }

}

