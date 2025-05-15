package com.furious.vehicle.inspect.application

abstract class UnitUseCase<IN> {

    abstract fun execute(input: IN)

}