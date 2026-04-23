package com.nwsweather.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AcUnit
import androidx.compose.material.icons.outlined.Cloud
import androidx.compose.material.icons.outlined.Grain
import androidx.compose.material.icons.outlined.NightsStay
import androidx.compose.material.icons.outlined.Thunderstorm
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.ui.graphics.vector.ImageVector
import com.nwsweather.ui.theme.WeatherMood
import com.nwsweather.ui.theme.mapWeatherMood

fun weatherIconForForecast(
    forecast: String,
    isDaytime: Boolean
): ImageVector {
    return when (mapWeatherMood(forecast, isDaytime)) {
        WeatherMood.SUNNY -> Icons.Outlined.WbSunny
        WeatherMood.CLOUDY -> Icons.Outlined.Cloud
        WeatherMood.RAIN -> Icons.Outlined.Grain
        WeatherMood.STORM -> Icons.Outlined.Thunderstorm
        WeatherMood.SNOW -> Icons.Outlined.AcUnit
        WeatherMood.CLEAR_NIGHT -> Icons.Outlined.NightsStay
        WeatherMood.CLOUDY_NIGHT -> Icons.Outlined.Cloud
    }
}
