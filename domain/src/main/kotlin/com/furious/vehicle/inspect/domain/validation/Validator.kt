package com.furious.vehicle.inspect.domain.validation

abstract class Validator(private val handler: ValidationHandler) {

    abstract fun validate()

    protected fun validationHandler(): ValidationHandler = handler

}