package com.furious.vehicle.inspect.domain.utils

import java.time.Instant
import java.time.temporal.ChronoUnit

object InstantUtils {

    fun now(): Instant = Instant.now().truncatedTo(ChronoUnit.MICROS)
}