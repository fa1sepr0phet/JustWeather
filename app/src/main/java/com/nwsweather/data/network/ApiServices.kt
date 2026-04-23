package com.nwsweather.data.network

import com.nwsweather.data.model.NwsAlertsResponse
import com.nwsweather.data.model.NwsForecastResponse
import com.nwsweather.data.model.NwsPointsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface NwsApi {
    @GET("points/{lat},{lon}")
    suspend fun getPointMetadata(
        @Path("lat") lat: String,
        @Path("lon") lon: String
    ): NwsPointsResponse

    @GET
    suspend fun getForecast(@Url url: String): NwsForecastResponse

    @GET("alerts/active")
    suspend fun getActiveAlerts(
        @Query("point") point: String
    ): NwsAlertsResponse
}
