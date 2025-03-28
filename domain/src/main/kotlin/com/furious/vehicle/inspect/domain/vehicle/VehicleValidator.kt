package com.furious.vehicle.inspect.domain.vehicle

import com.furious.vehicle.inspect.domain.validation.AppError
import com.furious.vehicle.inspect.domain.validation.ValidationHandler
import com.furious.vehicle.inspect.domain.validation.Validator
import java.time.Year

class VehicleValidator(
    private val aVehicle: Vehicle, aHandler: ValidationHandler
) : Validator(aHandler) {

    companion object {
        private const val MAKE_MAX_LENGTH = 20
        private const val MAKE_MIN_LENGTH = 2
        private const val MODEL_MAX_LENGTH = 30
        private const val MODEL_MIN_LENGTH = 2
    }

    override fun validate() {
        checkMake()
        checkModel()
        checkYear()
        checkLicensePlate()
        checkMileage()
    }

    private fun checkMake() {
        val name = aVehicle.make

        if (name.isEmpty()) {
            validationHandler().append(AppError("'make' cannot be empty"))
            return
        }

        if (name.isBlank()) {
            validationHandler().append(AppError("'make' cannot be blank"))
            return
        }

        val nameLength = name.trim().length
        if (nameLength < MAKE_MIN_LENGTH || nameLength > MAKE_MAX_LENGTH) {
            validationHandler().append(AppError("'make' must be between 2 and 20 characters"))
        }
    }

    private fun checkModel() {
        val name = aVehicle.model

        if (name.isEmpty()) {
            validationHandler().append(AppError("'model' cannot be empty"))
            return
        }

        if (name.isBlank()) {
            validationHandler().append(AppError("'model' cannot be blank"))
            return
        }

        val nameLength = name.trim().length
        if (nameLength < MODEL_MIN_LENGTH || nameLength > MODEL_MAX_LENGTH) {
            validationHandler().append(AppError("'model' must be between 2 and 30 characters"))
        }
    }

    private fun checkYear() {
        val year = aVehicle.year

        if (year < 0) {
            validationHandler().append(AppError("'year' cannot be negative"))
            return
        }

        if (year > Year.now().value + 1) {
            validationHandler().append(AppError("'year' cannot be more than 2 years ahead"))
            return
        }
    }

    private fun checkLicensePlate() {
        val errors = aVehicle.licensePlate.validate()

        if (errors.isNotEmpty()) {
            errors.forEach(validationHandler()::append)
        }
    }

    private fun checkMileage() {
        if (aVehicle.mileage == null) {
            return
        }
        val errors = aVehicle.mileage!!.validate()

        if (errors.isNotEmpty()) {
            errors.forEach(validationHandler()::append)
        }
    }
}