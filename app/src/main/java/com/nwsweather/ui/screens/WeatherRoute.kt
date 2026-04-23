package com.nwsweather.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nwsweather.presentation.WeatherViewModel

@Composable
fun WeatherRoute(
    viewModel: WeatherViewModel,
    onRequestCurrentLocation: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    WeatherScreen(
        uiState = uiState,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onSaveLabelChanged = viewModel::onSaveLabelChanged,
        onThemeChanged = viewModel::onThemeChanged,
        onSearchAddress = viewModel::searchAddress,
        onSavedLocationClick = viewModel::loadSavedLocation,
        onRefresh = viewModel::refreshForecast,
        onDismissError = viewModel::dismissError,
        onUseCurrentLocation = onRequestCurrentLocation
    )
}
