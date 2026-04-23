package com.nwsweather.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nwsweather.data.local.SavedLocationEntity
import com.nwsweather.data.repository.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherViewModel(
    private val repository: WeatherRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    init {
        observeSavedLocations()
    }

    fun onSearchQueryChanged(value: String) {
        _uiState.update { it.copy(searchQuery = value) }
    }

    fun onSaveLabelChanged(value: String) {
        _uiState.update { it.copy(saveLabel = value) }
    }

    fun onThemeChanged(theme: AppTheme) {
        _uiState.update { it.copy(theme = theme) }
    }

    fun dismissError() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    fun searchAddress() {
        val current = _uiState.value
        val query = current.searchQuery.trim()
        if (query.isBlank()) return

        launchLoad {
            repository.loadForecastForAddress(
                address = query,
                label = current.saveLabel.trim().ifBlank { null }
            )
        }
    }

    fun fetchCurrentLocation() {
        launchLoad {
            repository.loadForecastForCurrentLocation()
        }
    }

    fun loadSavedLocation(location: SavedLocationEntity) {
        launchLoad {
            repository.loadForecastForSavedLocation(location)
        }
    }

    fun refreshForecast() {
        val forecastResult = _uiState.value.forecastResult ?: return
        launchLoad {
            repository.loadForecastForCoordinates(
                latitude = forecastResult.latitude,
                longitude = forecastResult.longitude,
                source = forecastResult.source
            )
        }
    }

    fun onLocationPermissionDenied() {
        _uiState.update {
            it.copy(
                isLoading = false,
                errorMessage = "Location permission was denied. You can still search by address."
            )
        }
    }

    private fun observeSavedLocations() {
        viewModelScope.launch {
            repository.observeSavedLocations().collect { locations ->
                _uiState.update { it.copy(savedLocations = locations) }
            }
        }
    }

    private fun launchLoad(block: suspend () -> com.nwsweather.data.repository.ForecastLoadResult) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            runCatching { block() }
                .onSuccess { result ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            forecastResult = result,
                            locationName = result.locationName,
                            errorMessage = null
                        )
                    }
                }
                .onFailure { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "Something went sideways."
                        )
                    }
                }
        }
    }
}
