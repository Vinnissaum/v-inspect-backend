package com.furious.vehicle.inspect.domain.exceptions

import com.furious.vehicle.inspect.domain.AggregateRoot
import com.furious.vehicle.inspect.domain.Identifier
import com.furious.vehicle.inspect.domain.validation.AppError
import kotlin.reflect.KClass

open class NotFoundException protected constructor(
    message: String, errors: List<AppError>
) : DomainException(message, errors) {

    companion object {
        fun with(
            aggregate: KClass<out AggregateRoot<*>>, id: Identifier
        ): NotFoundException {
            val error = "${aggregate.simpleName} with ID ${id.getValue()} was not found"
            return NotFoundException(error, emptyList())
        }
    }
}