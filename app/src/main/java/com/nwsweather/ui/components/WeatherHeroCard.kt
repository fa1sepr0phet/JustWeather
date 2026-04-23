package com.nwsweather.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nwsweather.data.model.NwsForecastPeriod

@Composable
fun WeatherHeroCard(
    period: NwsForecastPeriod,
    locationName: String,
    cardColor: Color = Color.White.copy(alpha = 0.88f),
    textColor: Color = MaterialTheme.colorScheme.onSurface,
    onClick: () -> Unit = {}
) {
    val forecastText = period.shortForecast ?: period.name
    val icon = weatherIconForForecast(
        forecast = forecastText,
        isDaytime = period.isDaytime
    )

    ElevatedCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(
            containerColor = cardColor
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = locationName,
                style = MaterialTheme.typography.titleMedium,
                color = textColor
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = forecastText,
                    modifier = Modifier.size(42.dp),
                    tint = MaterialTheme.colorScheme.primary
                )

                Column {
                    Text(
                        text = "${period.temperature}°${period.temperatureUnit}",
                        style = MaterialTheme.typography.displayMedium,
                        color = textColor
                    )

                    Text(
                        text = forecastText,
                        style = MaterialTheme.typography.titleLarge,
                        color = textColor
                    )
                }
            }

            period.detailedForecast?.takeIf { it.isNotBlank() }?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Wind: ${period.windSpeed ?: "--"} ${period.windDirection ?: ""}".trim(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor.copy(alpha = 0.82f),
                    modifier = Modifier.wrapContentWidth()
                )

                val precip = period.probabilityOfPrecipitation?.value?.toInt()
                if (precip != null) {
                    Text(
                        text = "Rain: $precip%",
                        style = MaterialTheme.typography.bodyMedium,
                        color = textColor.copy(alpha = 0.82f)
                    )
                }
            }
        }
    }
}
