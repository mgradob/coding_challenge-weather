package com.mgb.codingchallenge.weather.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mgb.codingchallenge.weather.models.WeatherModel
import com.mgb.codingchallenge.weather.providers.WeatherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherProvider: WeatherProvider
) : ViewModel() {
    private val search = MutableStateFlow("")
    val searchFlow = search.asStateFlow()

    val weatherState = MutableStateFlow<WeatherModel?>(null)

    init {
        viewModelScope.launch {
            searchFlow.debounce(1000).collect {
                getWeatherFor(it)
            }
        }
    }

    fun setSearch(term: String) {
        search.value = term
    }

    fun getWeatherFor(lat: Double, lon: Double) {
        viewModelScope.launch {
            val weatherForLocation =
                weatherProvider.getWeatherForLocation(lat, lon)

            weatherState.value = WeatherModel.fromWeatherResponse(weatherForLocation)
        }
    }

    fun getWeatherFor(location: String) {
        viewModelScope.launch {
            val weatherForLocation =
                weatherProvider.getWeatherForLocation(location)

            weatherState.value = WeatherModel.fromWeatherResponse(weatherForLocation)
        }
    }
}