package com.example.mausam.widgets

import android.content.Context
import android.icu.text.CaseMap.Title
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mausam.model.Favourite
import com.example.mausam.navigation.WeatherScreens
import com.example.mausam.screens.favourite.FavouriteViewModel

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    favouriteViewModel: FavouriteViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {},
    ) {

    val showDialog = remember {
        mutableStateOf(false)
    }
    if (showDialog.value) {
        ShowSettingDropDownMenu(showDialog = showDialog, navController = navController)
    }

    val showIt = remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current


    TopAppBar(
        title = {
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors
            (
            containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(elevation),


            ),
        actions = {
            if (isMainScreen) {
                IconButton(onClick = {
                    onAddActionClicked.invoke()
                }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                }
                IconButton(onClick = {
                    showDialog.value = !showDialog.value
                }) {
                    Icon(
                        imageVector = Icons.Rounded.MoreVert,
                        contentDescription = null
                    )
                }
            } else Box {}
        },
        navigationIcon = {
            if (icon != null) {
                Icon(imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.clickable {
                        onButtonClicked.invoke()
                    })
            }
            if (isMainScreen){
                val isAlreadyFavList = favouriteViewModel
                    .fav_List.collectAsState().value.filter { item ->
                        (item.city == title.split(",")[0])

                    }
                if(isAlreadyFavList.isNullOrEmpty())
                {
                    Icon(imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        modifier = Modifier
                            .scale(0.9f)
                            .clickable {
                                favouriteViewModel.insertFavourite(
                                    Favourite(
                                        city = title.split(",")[0],
                                        country = title.split(",")[1]
                                    )).run {
                                        showIt.value = true
                                }
                            },
                        tint = Color.Red.copy(alpha = 0.6f)
                    )
                }else
                {
                    showIt.value =false
                    Box{}
                }

                ShowToast(context = context , showIt )



            }
        },

        )


}

@Composable
fun ShowToast(context: Context, showIt: MutableState<Boolean>) {
    if(showIt.value){
        Toast.makeText(context , "Added to Favourites",
            Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun ShowSettingDropDownMenu(
    showDialog: MutableState<Boolean>,
    navController: NavController
) {

    var expanded = remember {
        mutableStateOf(true)
    }
    val items = listOf("About", "Favourites", "Settings")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 45.dp, right = 20.dp)
    ) {
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier
                .width(140.dp)
                .background(Color.White)
        ) {
            items.forEachIndexed { index, text ->
                DropdownMenuItem(onClick = {
                    expanded.value = false
                    showDialog.value = false
                }, text = {
                    Text(text = text,modifier = Modifier.clickable {
                        navController.navigate(
                            when (text) {
                                "About" -> WeatherScreens.AboutScreen.name
                                "Favourites" ->WeatherScreens.FavouriteScreen.name
                                else -> WeatherScreens.SettingsScreen.name
                            }
                        )
                    })
                },
                    leadingIcon = {
                        Image(
                            imageVector = when (text) {
                                "About" -> Icons.Default.Info
                                "Favourites" -> Icons.Default.FavoriteBorder
                                else -> Icons.Default.Settings
                            },
                            contentDescription = null
                        )
                    })
            }
        }
    }
}
