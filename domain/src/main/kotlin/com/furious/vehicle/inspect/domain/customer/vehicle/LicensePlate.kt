package com.furious.vehicle.inspect.domain.customer.vehicle

import com.furious.vehicle.inspect.domain.ValueObject
import com.furious.vehicle.inspect.domain.validation.AppError

data class LicensePlate private constructor(private val value: String) : ValueObject() {
    companion object {
        fun create(value: String): LicensePlate = LicensePlate(value)
    }

    fun validate(): List<AppError> {
        val trimmedValue = value.trim()

        if (trimmedValue.isEmpty()) {
            return listOf(AppError("'license plate' cannot be empty"))
        }

        val regex = Regex("^[A-Z0-9-]+\$")

        if (!regex.matches(trimmedValue)) {
            return listOf(AppError("invalid 'license plate' format"))
        }

        return listOf()
    }

}