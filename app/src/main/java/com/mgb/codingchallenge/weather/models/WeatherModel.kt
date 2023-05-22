package com.mgb.codingchallenge.weather.models

data class WeatherModel(
    val location: String,
    val temp: Double,
    val feelsLike: Double,
    val min: Double,
    val max: Double,
) {
    companion object {
        fun fromWeatherResponse(weatherResponse: WeatherResponse): WeatherModel {
            return WeatherModel(
                weatherResponse.name,
                weatherResponse.main.temp,
                weatherResponse.main.feelsLike,
                weatherResponse.main.tempMin,
                weatherResponse.main.tempMax
            )
        }
    }
}
