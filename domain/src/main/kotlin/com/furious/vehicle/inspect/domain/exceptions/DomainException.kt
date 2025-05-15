package com.furious.vehicle.inspect.domain.exceptions

import com.furious.vehicle.inspect.domain.validation.AppError

open class DomainException(aMessage: String?, private val anErrors: List<AppError>) : NoStackTraceException(aMessage) {

    companion object Factory {
        fun with(anError: AppError): DomainException = DomainException(anError.message, listOf(anError))
        fun with(anErrors: List<AppError>): DomainException = DomainException("", anErrors)
    }

    fun getErrors(): List<AppError> = anErrors

}