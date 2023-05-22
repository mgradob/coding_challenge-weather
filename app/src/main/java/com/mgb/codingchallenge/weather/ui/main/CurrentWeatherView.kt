package com.mgb.codingchallenge.weather.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.mgb.codingchallenge.weather.models.WeatherModel

@Composable
fun CurrentWeatherView(
    weatherModel: WeatherModel
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(weatherModel.location)
        Text("Current temp: ${weatherModel.temp} ºF")
        Text("Feels like: ${weatherModel.feelsLike} ºF")
        Text("Max: ${weatherModel.max} ºF")
        Text("Min: ${weatherModel.min} ºF")
    }
}

@Composable
@Preview
fun CurrentWeatherPreview() {
    CurrentWeatherView(WeatherModel("Test", 10.0, 12.0, 10.0, 15.0))
}