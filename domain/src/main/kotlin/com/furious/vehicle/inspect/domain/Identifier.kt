package com.furious.vehicle.inspect.domain

abstract class Identifier : ValueObject() {
    abstract fun getValue(): Any
}