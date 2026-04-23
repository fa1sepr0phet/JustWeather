package com.nwsweather.location

data class DeviceCoordinates(
    val latitude: Double,
    val longitude: Double
)

interface DeviceLocationClient {
    suspend fun getCurrentLocation(): DeviceCoordinates?
}
