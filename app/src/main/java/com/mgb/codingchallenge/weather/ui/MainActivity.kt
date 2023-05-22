package com.mgb.codingchallenge.weather.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.mgb.codingchallenge.weather.models.Location
import com.mgb.codingchallenge.weather.ui.main.CurrentWeatherView
import com.mgb.codingchallenge.weather.ui.main.WeatherSearchView
import com.mgb.codingchallenge.weather.ui.theme.CodingChallengeWeatherTheme
import com.mgb.codingchallenge.weather.ui.theme.PaddingLarge
import com.mgb.codingchallenge.weather.utils.getLastSearched
import com.mgb.codingchallenge.weather.utils.setLastSearched
import com.mgb.codingchallenge.weather.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            for (location in locationResult.locations) {
                locationState.value = Location(location.latitude, location.longitude)
            }
        }
    }

    private val locationState = MutableStateFlow(Location(0.0, 0.0))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            // TODO: review permissions, since it's not triggering the correct behavior
            rememberLauncherForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissionsMap ->
                val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
                if (areGranted) {
                    startLocationUpdates()
                }
            }

            MainScreen(
                sharedPreferences = getPreferences(Context.MODE_PRIVATE),
                onLocationClick = { startLocationUpdates() },
            )
        }
    }

    override fun onPause() {
        super.onPause()

        locationCallback.let { fusedLocationClient.removeLocationUpdates(it) }
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        locationCallback.let {
            val locationRequest = LocationRequest.create().apply {
                interval = 10000
                fastestInterval = 5000
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                it,
                Looper.getMainLooper()
            )
        }
    }
}

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel(),
    sharedPreferences: SharedPreferences,
    onLocationClick: () -> Unit
) {
    val weatherState = viewModel.weatherState.collectAsState()

    val lastSearched = sharedPreferences.getLastSearched() ?: ""
    viewModel.setSearch(lastSearched)

    CodingChallengeWeatherTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(PaddingLarge),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                WeatherSearchView(viewModel.searchFlow) {
                    viewModel.setSearch(it)
                    sharedPreferences.setLastSearched(it)
                }

                if (weatherState.value != null)
                    CurrentWeatherView(weatherState.value!!)

                Button(onClick = onLocationClick) {
                    Text("My location")
                }
            }
        }
    }
}
