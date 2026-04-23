package com.nwsweather

import android.app.Application
import com.nwsweather.worker.WidgetRefreshWorker

class NwsWeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()
        WidgetRefreshWorker.schedulePeriodic(this)
    }
}
