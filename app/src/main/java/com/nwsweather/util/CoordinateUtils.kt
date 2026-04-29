package com.nwsweather.util

import kotlin.math.round

/**
 * Rounds a coordinate to 3 decimal places (approx 111m accuracy).
 * Privacy: This prevents sending exact house-level locations to external APIs.
 */
fun Double.roundCoordinate(): Double = round(this * 1000.0) / 1000.0
