package com.furious.vehicle.inspect.domain.customer.vehicle

import com.furious.vehicle.inspect.domain.ValueObject
import com.furious.vehicle.inspect.domain.validation.AppError


data class Mileage private constructor(val unit: UnitOfMeasurement, private val value: Double) : ValueObject() {
    companion object {
        fun create(unit: UnitOfMeasurement, value: Double): Mileage = Mileage(unit, value)
    }

    fun validate(): List<AppError> {
        if (value < 0) {
            return listOf(AppError("'mileage' cannot be negative"))
        }
        return listOf()
    }

    // Convert miles to kilometers (or vice versa) based on the unit
    fun inKilometers(): Double {
        return if (unit == UnitOfMeasurement.MILES) {
            value * 1.60934  // Convert miles to kilometers
        } else {
            value  // Already in kilometers, so just return the value
        }
    }

    fun inMiles(): Double {
        return if (unit == UnitOfMeasurement.KM) {
            value / 1.60934  // Convert kilometers to miles
        } else {
            value  // Already in miles, so just return the value
        }
    }

    override fun toString(): String {
        return "$value ${unit.symbol}"
    }
}

enum class UnitOfMeasurement(val symbol: String) {
    MILES("miles"), KM("km")
}

