package com.furious.vehicle.inspect.domain.validation.handler

import com.furious.vehicle.inspect.domain.exceptions.DomainException
import com.furious.vehicle.inspect.domain.validation.AppError
import com.furious.vehicle.inspect.domain.validation.ValidationHandler

class Notification(private val errors: MutableList<AppError>) : ValidationHandler {

    companion object Factory {
        fun create(): Notification = Notification(arrayListOf())
        fun create(anAppError: AppError): Notification = Notification(arrayListOf()).append(anAppError)
        fun create(t: Throwable): Notification = create(AppError(t.message ?: "Unknown error"))
    }

    override fun append(anAppError: AppError): Notification {
        this.errors.add(anAppError)
        return this
    }

    override fun append(anHandler: ValidationHandler): Notification {
        this.errors.addAll(anHandler.getErrors().orEmpty())
        return this
    }

    override fun <T> validate(aValidation: ValidationHandler.Validation<T>): T? {
        try {
            return aValidation.validate()
        } catch (ex: DomainException) {
            errors.addAll(ex.getErrors())
        } catch (t: Throwable) {
            errors.add(AppError(t.message ?: "Unknown error"))
        }
        return null
    }

    override fun getErrors(): List<AppError> = this.errors

}