package com.nwsweather.location

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class AndroidLocationClient(
    context: Context
) : DeviceLocationClient {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): DeviceCoordinates? {
        val currentLocation = suspendCancellableCoroutine<DeviceCoordinates?> { continuation ->
            val tokenSource = CancellationTokenSource()
            fusedLocationClient.getCurrentLocation(
                Priority.PRIORITY_HIGH_ACCURACY,
                tokenSource.token
            ).addOnSuccessListener { location ->
                continuation.resume(
                    location?.let { DeviceCoordinates(it.latitude, it.longitude) }
                )
            }.addOnFailureListener {
                continuation.resume(null)
            }
            continuation.invokeOnCancellation { tokenSource.cancel() }
        }

        if (currentLocation != null) return currentLocation

        return suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    continuation.resume(
                        location?.let { DeviceCoordinates(it.latitude, it.longitude) }
                    )
                }
                .addOnFailureListener {
                    continuation.resume(null)
                }
        }
    }
}
