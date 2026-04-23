package com.nwsweather.util

import kotlin.math.round

fun Double.roundCoordinate(): Double = round(this * 10_000.0) / 10_000.0
