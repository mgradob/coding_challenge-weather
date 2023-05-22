package com.mgb.codingchallenge.weather.ui.main

import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun WeatherSearchView(searchFlow: StateFlow<String>, onSearch: (String) -> Unit) {
    val searchState = searchFlow.collectAsState()

    OutlinedTextField(
        value = searchState.value,
        onValueChange = onSearch
    )
}

@Composable
@Preview
private fun WeatherSearchPreview() {
    WeatherSearchView(MutableStateFlow("")) {}
}
