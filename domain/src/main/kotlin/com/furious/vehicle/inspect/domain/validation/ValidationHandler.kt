package com.furious.vehicle.inspect.domain.validation

interface ValidationHandler {

    fun append(anAppError: AppError): ValidationHandler

    fun append(anHandler: ValidationHandler): ValidationHandler

    fun <T> validate(aValidation: Validation<T>): T

    fun getErrors(): List<AppError>

    fun hasError(): Boolean {
        return getErrors().isNotEmpty() ?: false
    }

    fun firstError(): AppError? {
        return getErrors().first()
    }

    interface Validation<T> {
        fun validate(): T
    }

}