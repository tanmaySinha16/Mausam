package com.example.mausam.screens.favourite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mausam.model.Favourite
import com.example.mausam.navigation.WeatherScreens
import com.example.mausam.widgets.WeatherAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreen(
    navController: NavHostController,
    favouriteViewModel: FavouriteViewModel = hiltViewModel()
) {

    Scaffold(topBar = {
        WeatherAppBar(
            title = "   Favourite Cities",
            icon = Icons.Default.ArrowBack,
            false,
            navController = navController
        ) {
            navController.popBackStack()
        }
    }) {
        Box(modifier = Modifier.padding(it))
        {
            Surface(
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val list = favouriteViewModel.fav_List.collectAsState().value
                    LazyColumn {
                        items(list) {
                            CityRow(it, navController = navController, favouriteViewModel)
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun CityRow(
    favourite: Favourite,
    navController: NavHostController,
    favouriteViewModel: FavouriteViewModel
) {
    Surface(
        Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                navController.navigate(WeatherScreens.MainScreen.name+"/${favourite.city}")
            },
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)),
        color = Color(0xFFB2DFDB)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(start = 50.dp, end = 50.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = favourite.city)
            Surface(modifier = Modifier.padding(0.dp),
            shape = CircleShape,
            color = Color(0xFFD1E3E1)) {
                Text(
                    text = favourite.country,
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Icon(
                imageVector = Icons.Rounded.Delete , contentDescription = null,
                modifier = Modifier.clickable {
                     favouriteViewModel.deleteFavourite(favourite = favourite)
                },
                tint = Color.Red.copy(alpha = 0.3f)
            )
        }
    }
}
