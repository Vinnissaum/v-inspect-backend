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

    override fun <T> validate(aValidation: () -> T): T? {
        return try {
            aValidation()
        } catch (ex: DomainException) {
            errors.addAll(ex.getErrors())
            null
        } catch (t: Throwable) {
            errors.add(AppError(t.message))
            null
        }
    }

    override fun getErrors(): List<AppError> = this.errors

}