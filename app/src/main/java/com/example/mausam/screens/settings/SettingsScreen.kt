package com.example.mausam.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.mausam.model.Unit
import com.example.mausam.screens.settings.SettingsViewModel
import com.example.mausam.widgets.WeatherAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController,
                   settingsViewModel: SettingsViewModel = hiltViewModel()) {
    var unitToggleState  =  remember{
        mutableStateOf(false)
    }
    val choiceFromDb = settingsViewModel.unitList.collectAsState().value
    val measurementUnits = listOf("Imperial (F)" ,"Metric (C)")
    val defaultChoice = if(choiceFromDb.isNullOrEmpty()) measurementUnits[0]
                        else
                            choiceFromDb[0].unit
    val choiceState = remember {
        mutableStateOf(defaultChoice)
    }
    Scaffold(topBar = {
            WeatherAppBar(
                title = "Settings",
                icon = Icons.Default.ArrowBack,
                navController = navController,
                isMainScreen = false
            ){
                navController.popBackStack()
            }
    }) {
        Surface(modifier = Modifier
            .padding(it)
            .fillMaxWidth()
            .fillMaxHeight()) {
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Change Units of Measurement",
                modifier = Modifier.padding(bottom = 15.dp))

                IconToggleButton(
                    checked = !unitToggleState.value ,
                    onCheckedChange = {
                        unitToggleState.value = !it
                       choiceState.value =  if(unitToggleState.value){
                            "Imperial (F)"
                        }
                        else
                            "Metric (C)"
                    }, modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .clip(shape = RectangleShape)
                        .padding(5.dp)
                        .background(Color.Magenta.copy(alpha = 0.4f))) {


                        Text(text = if(unitToggleState.value) "Fahrenheit °F" else "Celsius °C" ,
                            color = Color.Black)
                }
                Button(onClick = {
                    settingsViewModel.deleteAllUnits()
                    settingsViewModel.insertUnit(Unit(unit = choiceState.value))
                },
                modifier = Modifier
                    .padding(3.dp)
                    .align(CenterHorizontally)
                    , shape = RoundedCornerShape(34.dp),
                    colors= ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEFBE42)
                    )
                ) {
                        Text(text = "Save" , color = Color.Black)
                }
            }
        }

    }

}