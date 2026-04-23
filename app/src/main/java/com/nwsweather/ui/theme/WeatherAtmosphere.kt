package com.nwsweather.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

enum class WeatherMood {
    SUNNY,
    CLOUDY,
    RAIN,
    STORM,
    SNOW,
    CLEAR_NIGHT,
    CLOUDY_NIGHT
}

data class WeatherVisuals(
    val background: Brush,
    val cardColor: Color,
    val onCardTextColor: Color,
    val appBarContentColor: Color
)

fun mapWeatherMood(forecast: String, isDay: Boolean): WeatherMood {
    val text = forecast.lowercase()

    return when {
        text.contains("storm") || text.contains("thunder") -> WeatherMood.STORM
        text.contains("snow") || text.contains("flurr") || text.contains("sleet") -> WeatherMood.SNOW
        text.contains("rain") || text.contains("shower") || text.contains("drizzle") -> WeatherMood.RAIN
        text.contains("cloud") || text.contains("overcast") -> {
            if (isDay) WeatherMood.CLOUDY else WeatherMood.CLOUDY_NIGHT
        }
        else -> {
            if (isDay) WeatherMood.SUNNY else WeatherMood.CLEAR_NIGHT
        }
    }
}

fun visualsForMood(mood: WeatherMood): WeatherVisuals {
    val darkText = Color.Black
    val lightText = Color.White
    val lightCard = Color.White.copy(alpha = 0.92f)

    return when (mood) {
        WeatherMood.SUNNY -> WeatherVisuals(
            background = Brush.verticalGradient(
                listOf(
                    Color(0xFF69B7FF),
                    Color(0xFFAAD8FF),
                    Color(0xFFFFD67A)
                )
            ),
            cardColor = lightCard,
            onCardTextColor = darkText,
            appBarContentColor = lightText
        )

        WeatherMood.CLOUDY -> WeatherVisuals(
            background = Brush.verticalGradient(
                listOf(
                    Color(0xFF8FA3B0),
                    Color(0xFFB8C7D0),
                    Color(0xFFE2E8EC)
                )
            ),
            cardColor = lightCard,
            onCardTextColor = darkText,
            appBarContentColor = lightText
        )

        WeatherMood.RAIN -> WeatherVisuals(
            background = Brush.verticalGradient(
                listOf(
                    Color(0xFF355C7D),
                    Color(0xFF4F6D7A),
                    Color(0xFF7C98A3)
                )
            ),
            cardColor = lightCard,
            onCardTextColor = darkText,
            appBarContentColor = lightText
        )

        WeatherMood.STORM -> WeatherVisuals(
            background = Brush.verticalGradient(
                listOf(
                    Color(0xFF16213E),
                    Color(0xFF23395B),
                    Color(0xFF3C4F76)
                )
            ),
            cardColor = lightCard,
            onCardTextColor = darkText,
            appBarContentColor = lightText
        )

        WeatherMood.SNOW -> WeatherVisuals(
            background = Brush.verticalGradient(
                listOf(
                    Color(0xFFDDEAF7),
                    Color(0xFFF4F8FC),
                    Color(0xFFFFFFFF)
                )
            ),
            cardColor = Color.White.copy(alpha = 0.92f),
            onCardTextColor = darkText,
            appBarContentColor = darkText
        )

        WeatherMood.CLEAR_NIGHT -> WeatherVisuals(
            background = Brush.verticalGradient(
                listOf(
                    Color(0xFF09111F),
                    Color(0xFF14243D),
                    Color(0xFF223A5E)
                )
            ),
            cardColor = lightCard,
            onCardTextColor = darkText,
            appBarContentColor = lightText
        )

        WeatherMood.CLOUDY_NIGHT -> WeatherVisuals(
            background = Brush.verticalGradient(
                listOf(
                    Color(0xFF1A2633),
                    Color(0xFF2C3E50),
                    Color(0xFF415B76)
                )
            ),
            cardColor = lightCard,
            onCardTextColor = darkText,
            appBarContentColor = lightText
        )
    }
}
