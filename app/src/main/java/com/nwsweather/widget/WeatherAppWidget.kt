package com.nwsweather.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.action.actionStartActivity
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.action.clickable
import androidx.glance.layout.*
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.nwsweather.MainActivity
import com.nwsweather.R
import com.nwsweather.data.local.AppDatabase
import com.nwsweather.data.local.WeatherSnapshotEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WeatherAppWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val snapshot = withContext(Dispatchers.IO) {
            AppDatabase.getInstance(context).weatherSnapshotDao().getLatest()
        }

        provideContent {
            WeatherWidgetContent(snapshot = snapshot)
        }
    }
}

@Composable
private fun WeatherWidgetContent(snapshot: WeatherSnapshotEntity?) {
    Box(
        modifier = GlanceModifier
            .fillMaxSize()
            .appWidgetBackground()
            .cornerRadius(24.dp)
            .background(backgroundColorFor(snapshot))
            .clickable(actionStartActivity<MainActivity>())
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (snapshot == null) {
            EmptyWeatherWidget()
        } else {
            LoadedWeatherWidget(snapshot)
        }
    }
}

@Composable
private fun EmptyWeatherWidget() {
    Column(
        modifier = GlanceModifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "NWS Weather",
            style = TextStyle(
                color = ColorProvider(Color.White),
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = GlanceModifier.height(8.dp))
        Text(
            text = "Open the app to load your first forecast",
            style = TextStyle(color = ColorProvider(Color.White))
        )
    }
}

@Composable
private fun LoadedWeatherWidget(snapshot: WeatherSnapshotEntity) {
    Column(
        modifier = GlanceModifier.fillMaxSize(),
        verticalAlignment = Alignment.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = GlanceModifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.Start
        ) {
            Column(modifier = GlanceModifier.defaultWeight()) {
                Text(
                    text = snapshot.locationName,
                    style = TextStyle(
                        color = ColorProvider(Color.White),
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 1
                )
                Spacer(modifier = GlanceModifier.height(4.dp))
                Text(
                    text = "Updated ${snapshot.updatedAtEpochMs.asWidgetTime()}",
                    style = TextStyle(color = ColorProvider(Color(0xDDEFFFFFF)))
                )
            }
            Text(
                text = "Refresh",
                modifier = GlanceModifier
                    .background(ColorProvider(Color(0x33FFFFFF)))
                    .cornerRadius(999.dp)
                    .clickable(actionRunCallback<RefreshWeatherAction>())
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                style = TextStyle(
                    color = ColorProvider(Color.White),
                    fontWeight = FontWeight.Medium
                )
            )
        }

        Spacer(modifier = GlanceModifier.height(18.dp))

        Text(
            text = "${snapshot.temperature}°${snapshot.temperatureUnit}",
            style = TextStyle(
                color = ColorProvider(Color.White),
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = GlanceModifier.height(8.dp))

        Text(
            text = snapshot.shortForecast,
            style = TextStyle(color = ColorProvider(Color.White)),
            maxLines = 2
        )

        Spacer(modifier = GlanceModifier.height(12.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                provider = ImageProvider(R.drawable.ic_widget_wind),
                contentDescription = null,
                modifier = GlanceModifier.width(14.dp).height(14.dp)
            )
            Spacer(modifier = GlanceModifier.width(6.dp))
            Text(
                text = "${snapshot.windSpeed} ${snapshot.windDirection}",
                style = TextStyle(color = ColorProvider(Color(0xE6FFFFFF)))
            )
        }
    }
}

private fun backgroundColorFor(snapshot: WeatherSnapshotEntity?): ColorProvider {
    val color = when {
        snapshot == null -> Color(0xFF31435F)
        snapshot.isDaytime -> Color(0xFF4F86C6)
        else -> Color(0xFF1F2A44)
    }
    return ColorProvider(color)
}

private fun Long.asWidgetTime(): String {
    val formatter = SimpleDateFormat("h:mm a", Locale.getDefault())
    return formatter.format(Date(this))
}
