package com.nwsweather.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.nwsweather.data.local.SavedLocationEntity
import com.nwsweather.presentation.AppTheme
import com.nwsweather.presentation.WeatherUiState
import com.nwsweather.ui.components.ForecastCard
import com.nwsweather.ui.components.LocationSearchCard
import com.nwsweather.ui.components.SavedLocationChips
import com.nwsweather.ui.components.WeatherAlertCard
import com.nwsweather.ui.components.WeatherHeroCard
import com.nwsweather.ui.theme.WeatherVisuals
import com.nwsweather.ui.theme.mapWeatherMood
import com.nwsweather.ui.theme.visualsForMood

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(
    uiState: WeatherUiState,
    onSearchQueryChanged: (String) -> Unit,
    onSaveLabelChanged: (String) -> Unit,
    onThemeChanged: (AppTheme) -> Unit,
    onSearchAddress: () -> Unit,
    onSavedLocationClick: (SavedLocationEntity) -> Unit,
    onRefresh: () -> Unit,
    onDismissError: () -> Unit,
    onUseCurrentLocation: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    var showThemeMenu by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val current = uiState.forecastResult?.currentPeriod

    val mood = mapWeatherMood(
        forecast = current?.shortForecast ?: "",
        isDay = current?.isDaytime ?: true
    )

    val openNwsWebsite = { lat: Double, lon: Double ->
        val url = "https://forecast.weather.gov/MapClick.php?lat=$lat&lon=$lon"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

    val visuals = if (uiState.theme == AppTheme.MIDNIGHT) {
        WeatherVisuals(
            background = Brush.verticalGradient(listOf(Color.Black, Color.Black)),
            cardColor = Color(0xFF121212),
            onCardTextColor = Color.White,
            appBarContentColor = Color.White
        )
    } else {
        visualsForMood(mood)
    }

    LaunchedEffect(uiState.errorMessage) {
        val message = uiState.errorMessage ?: return@LaunchedEffect
        snackbarHostState.showSnackbar(message)
        onDismissError()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(visuals.background)
    ) {
        if (uiState.theme != AppTheme.MIDNIGHT) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.14f),
                                Color.Transparent
                            )
                        )
                    )
            )
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent,
                        titleContentColor = visuals.appBarContentColor,
                        actionIconContentColor = visuals.appBarContentColor
                    ),
                    title = {
                        Text(
                            text = uiState.locationName ?: "NWS Weather",
                            style = MaterialTheme.typography.titleLarge
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = { showThemeMenu = true }
                        ) {
                            Icon(
                                Icons.Outlined.Palette,
                                contentDescription = "Change theme"
                            )
                            DropdownMenu(
                                expanded = showThemeMenu,
                                onDismissRequest = { showThemeMenu = false }
                            ) {
                                DropdownMenuItem(
                                    text = { Text("System") },
                                    onClick = {
                                        onThemeChanged(AppTheme.SYSTEM)
                                        showThemeMenu = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Light") },
                                    onClick = {
                                        onThemeChanged(AppTheme.LIGHT)
                                        showThemeMenu = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Dark") },
                                    onClick = {
                                        onThemeChanged(AppTheme.DARK)
                                        showThemeMenu = false
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Midnight") },
                                    onClick = {
                                        onThemeChanged(AppTheme.MIDNIGHT)
                                        showThemeMenu = false
                                    }
                                )
                            }
                        }

                        IconButton(
                            onClick = onRefresh,
                            enabled = uiState.forecastResult != null && !uiState.isLoading
                        ) {
                            Icon(
                                Icons.Outlined.Refresh,
                                contentDescription = "Refresh forecast"
                            )
                        }
                    }
                )
            },
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ) { innerPadding ->
            PullToRefreshBox(
                isRefreshing = uiState.isLoading,
                onRefresh = onRefresh,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        LocationSearchCard(
                            searchQuery = uiState.searchQuery,
                            saveLabel = uiState.saveLabel,
                            isLoading = uiState.isLoading,
                            onSearchQueryChanged = onSearchQueryChanged,
                            onSaveLabelChanged = onSaveLabelChanged,
                            onUseCurrentLocation = onUseCurrentLocation,
                            onSearchAddress = onSearchAddress,
                            hasAlerts = uiState.forecastResult?.alerts?.isNotEmpty() == true,
                            onAlertClick = {
                                uiState.forecastResult?.let { result ->
                                    openNwsWebsite(result.latitude, result.longitude)
                                }
                            },
                            cardColor = visuals.cardColor,
                            textColor = visuals.onCardTextColor
                        )
                    }

                    if (uiState.savedLocations.isNotEmpty()) {
                        item {
                            Text(
                                text = "Saved locations",
                                style = MaterialTheme.typography.titleMedium,
                                color = visuals.appBarContentColor
                            )
                        }
                        item {
                            SavedLocationChips(
                                locations = uiState.savedLocations,
                                onClick = onSavedLocationClick,
                                textColor = visuals.appBarContentColor
                            )
                        }
                    }

                    if (uiState.isLoading) {
                        item {
                            Text(
                                text = "Loading forecast…",
                                style = MaterialTheme.typography.bodyLarge,
                                color = visuals.appBarContentColor
                            )
                        }
                    }

                    val result = uiState.forecastResult
                    if (result != null) {
                        if (result.alerts.isNotEmpty()) {
                            items(result.alerts) { alert ->
                                WeatherAlertCard(alert = alert)
                            }
                        }

                        result.currentPeriod?.let { currentPeriod ->
                            item {
                                WeatherHeroCard(
                                    period = currentPeriod,
                                    locationName = result.locationName,
                                    cardColor = visuals.cardColor,
                                    textColor = visuals.onCardTextColor,
                                    onClick = { openNwsWebsite(result.latitude, result.longitude) }
                                )
                            }
                        }

                        if (result.upcomingPeriods.isNotEmpty()) {
                            item {
                                HorizontalDivider(color = visuals.appBarContentColor.copy(alpha = 0.28f))
                            }
                            item {
                                Text(
                                    text = "Upcoming forecast",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = visuals.appBarContentColor
                                )
                            }
                            items(result.upcomingPeriods) { period ->
                                ForecastCard(
                                    period = period,
                                    cardColor = visuals.cardColor,
                                    textColor = visuals.onCardTextColor,
                                    onClick = { openNwsWebsite(result.latitude, result.longitude) }
                                )
                            }
                        }
                    } else if (!uiState.isLoading) {
                        item {
                            Text(
                                text = "Search an address or use your current location to load a forecast.",
                                style = MaterialTheme.typography.bodyLarge,
                                color = visuals.appBarContentColor
                            )
                        }
                    }
                }
            }
        }
    }
}
