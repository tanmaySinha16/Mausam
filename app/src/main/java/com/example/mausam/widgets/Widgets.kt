package com.example.mausam.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.mausam.R
import com.example.mausam.model.WeatherItem
import com.example.mausam.utils.formatDate
import com.example.mausam.utils.formatDateTime
import com.example.mausam.utils.formatDecimals

@Composable
fun WeatherDetailRow(weather: WeatherItem) {
    val imageUrl = "https://openweathermap.org/img/wn/${weather.weather[0].icon}.png"
    Surface(
        Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(4.dp),
        color = Color.White
    ) {
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween){
            Text(text = formatDate(weather.dt).split(",")[0],
                modifier = Modifier.padding(5.dp))
            Surface(modifier = Modifier.padding(0.dp),
                shape = CircleShape,
                color = Color(0xFFFFC400)
            ) {
                Text(text = weather.weather[0].description,
                    modifier = Modifier.padding(6.dp),
                    style = MaterialTheme.typography.titleSmall)
            }
            WeatherStateImage(imageUrl = imageUrl)
            Text(text = buildAnnotatedString {
                withStyle(style = SpanStyle(
                    color = Color.Blue.copy(alpha = 0.7f),
                    fontWeight = FontWeight.SemiBold
                )
                ){
                    append(formatDecimals(weather.temp.max) +"°")
                }
                withStyle(style = SpanStyle(
                    color = Color.LightGray
                )
                ){
                    append(formatDecimals(weather.temp.min) +"°")
                }
            })
        }
    }
}

@Composable
fun SunsetSunriseRow(weather: WeatherItem) {
    Row(modifier = Modifier
        .padding(top = 15.dp, bottom = 6.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween){
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.sunrise), contentDescription =null,
                Modifier.size(30.dp))
            Text(text = formatDateTime(weather.sunrise), style = MaterialTheme.typography.titleSmall)
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Text(text = formatDateTime(weather.sunset), style = MaterialTheme.typography.titleSmall)
            Icon(painter = painterResource(id = R.drawable.sunset), contentDescription =null,
                Modifier.size(30.dp))
        }

    }
}



@Composable
fun HumidityWindPressureRow(weather: WeatherItem, isImperial: Boolean) {
    Row(modifier = Modifier
        .padding(12.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.humidity), contentDescription =null,
                Modifier.size(20.dp))
            Text(text = "${weather.humidity}%", style = MaterialTheme.typography.titleSmall)
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.pressure), contentDescription =null,
                Modifier.size(20.dp))
            Text(text = "${weather.pressure} psi", style = MaterialTheme.typography.titleSmall)
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.wind), contentDescription =null,
                Modifier.size(20.dp))
            Text(text = formatDecimals(weather.speed) +if(isImperial) " mph" else " m/s", style = MaterialTheme.typography.titleSmall)
        }
    }
}

@Composable
fun WeatherStateImage(imageUrl: String) {
    Image(painter = rememberImagePainter(
        imageUrl), contentDescription = null,
        modifier = Modifier.size(80.dp))
}

