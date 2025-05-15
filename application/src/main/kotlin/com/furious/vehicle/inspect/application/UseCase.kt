package com.furious.vehicle.inspect.application

abstract class UseCase<IN, OUT> {

    abstract fun execute(input: IN): OUT

}