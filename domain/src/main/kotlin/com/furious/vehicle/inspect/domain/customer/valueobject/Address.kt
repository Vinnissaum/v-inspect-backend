package com.furious.vehicle.inspect.domain.customer.valueobject

import com.furious.vehicle.inspect.domain.ValueObject
import com.furious.vehicle.inspect.domain.validation.AppError

data class Address private constructor(
    val street: String,
    val city: String,
    val state: String,
    val postalCode: String,
    val country: String,
    val apartment: String?
) : ValueObject() {
    companion object {
        fun create(
            street: String, city: String, state: String, postalCode: String, country: String, apartment: String? = null
        ): Address {
            val trimmedStreet = street.trim()
            val trimmedCity = city.trim()
            val trimmedState = state.trim()
            val trimmedPostalCode = postalCode.trim()
            val trimmedCountry = country.trim()
            val trimmedApartment = apartment?.trim()?.takeIf { it.isNotEmpty() }

            return Address(
                trimmedStreet, trimmedCity, trimmedState, trimmedPostalCode, trimmedCountry, trimmedApartment
            )
        }
    }

    fun validate(): List<AppError> {
        val errors = mutableListOf<AppError>()
        if (street.isEmpty()) {
            errors.add(AppError("'street' cannot be empty"))
        }
        if (city.isEmpty()) {
            errors.add(AppError("'city' cannot be empty"))
        }
        if (state.isEmpty()) {
            errors.add(AppError("'state' cannot be empty"))
        }
        if (postalCode.isEmpty()) {
            errors.add(AppError("'postal' code cannot be empty"))
        }
        if (country.isEmpty()) {
            errors.add(AppError("'country' cannot be empty"))
        }

        return errors
    }

    fun toFormattedString(): String {
        val apartmentPart = if (apartment != null) "$apartment, " else ""
        return "$apartmentPart$street, $city, $state $postalCode, $country"
    }
}
