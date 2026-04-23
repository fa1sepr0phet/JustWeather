package com.nwsweather.presentation

import com.nwsweather.data.local.SavedLocationEntity
import com.nwsweather.data.repository.ForecastLoadResult

enum class AppTheme {
    SYSTEM, LIGHT, DARK, MIDNIGHT
}

data class WeatherUiState(
    val searchQuery: String = "",
    val saveLabel: String = "",
    val isLoading: Boolean = false,
    val locationName: String? = null,
    val forecastResult: ForecastLoadResult? = null,
    val savedLocations: List<SavedLocationEntity> = emptyList(),
    val errorMessage: String? = null,
    val theme: AppTheme = AppTheme.SYSTEM
)
