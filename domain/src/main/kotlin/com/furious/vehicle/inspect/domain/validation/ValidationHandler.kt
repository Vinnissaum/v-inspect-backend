package com.furious.vehicle.inspect.domain.validation

interface ValidationHandler {

    fun append(anAppError: AppError): ValidationHandler

    fun append(anHandler: ValidationHandler): ValidationHandler

    fun <T> validate(aValidation: () -> T): T?

    fun getErrors(): List<AppError>

    fun hasError(): Boolean {
        return getErrors().isNotEmpty()
    }

    fun firstError(): AppError? {
        return getErrors().first()
    }

    interface Validation<T> {
        fun validate(): T
    }

}