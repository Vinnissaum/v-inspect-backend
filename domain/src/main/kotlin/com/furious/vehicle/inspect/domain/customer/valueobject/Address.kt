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
        if (street.isBlank()) {
            errors.add(AppError("Street cannot be blank"))
        }
        if (city.isBlank()) {
            errors.add(AppError("City cannot be blank"))
        }
        if (state.isBlank()) {
            errors.add(AppError("State cannot be blank"))
        }
        if (postalCode.isBlank()) {
            errors.add(AppError("Postal code cannot be blank"))
        }
        if (country.isBlank()) {
            errors.add(AppError("Country cannot be blank"))
        }

        return errors
    }

    fun toFormattedString(): String {
        val apartmentPart = if (apartment != null) "$apartment, " else ""
        return "$apartmentPart$street, $city, $state $postalCode, $country"
    }
}
