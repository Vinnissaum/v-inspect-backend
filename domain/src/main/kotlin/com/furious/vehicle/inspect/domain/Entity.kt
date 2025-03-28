package com.furious.vehicle.inspect.domain

import com.furious.vehicle.inspect.domain.validation.ValidationHandler

abstract class Entity<ID : Identifier> protected constructor(protected val id: ID) {

    abstract fun validate(handler: ValidationHandler)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Entity<*>

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}