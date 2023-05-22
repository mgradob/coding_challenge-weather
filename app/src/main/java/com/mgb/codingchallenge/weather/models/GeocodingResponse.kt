package com.mgb.codingchallenge.weather.models

data class GeocodingResponse(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String
)
