package com.example.mausam.screens.favourite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mausam.model.Favourite
import com.example.mausam.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val repository: WeatherDbRepository
) :ViewModel(){
    private val _favList = MutableStateFlow<List<Favourite>>(emptyList())
    val fav_List = _favList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO){
            repository.getFavourites().distinctUntilChanged()
                .collect(){ list ->
                    if(list.isNullOrEmpty()){
                        Log.d("TAG", ": Empty Fav")
                    }
                    else{
                        _favList.value = list
                        Log.d("TAG", "${fav_List.value}: ")
                    }
                }
        }
    }

    fun insertFavourite(favourite:Favourite) = viewModelScope.launch {
        repository.insertFavourite(favourite = favourite)
    }
    fun deleteFavourite(favourite: Favourite) = viewModelScope.launch {
        repository.deleteFavourite(favourite = favourite)
    }
    fun updateFavourite(favourite: Favourite) = viewModelScope.launch {
        repository.updateFavourite(favourite = favourite)
    }

}