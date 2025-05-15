package com.furious.vehicle.inspect.domain.validation.handler

import com.furious.vehicle.inspect.domain.exceptions.DomainException
import com.furious.vehicle.inspect.domain.validation.AppError
import com.furious.vehicle.inspect.domain.validation.ValidationHandler

class ThrowsValidationHandler : ValidationHandler {

    override fun append(anAppError: AppError): ValidationHandler {
        throw DomainException.with(anAppError)
    }

    override fun append(anHandler: ValidationHandler): ValidationHandler {
        throw DomainException.with(anHandler.getErrors())
    }

    override fun <T> validate(aValidation: () -> T): T? {
        return try {
            aValidation()
        } catch (ex: Exception) {
            throw DomainException.with(listOf(AppError(ex.message ?: "Unknown error")))
        }
    }

    override fun getErrors(): List<AppError> = listOf()

}