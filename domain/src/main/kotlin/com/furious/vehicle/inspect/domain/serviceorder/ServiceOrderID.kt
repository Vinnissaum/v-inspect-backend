package com.furious.vehicle.inspect.domain.serviceorder

import com.furious.vehicle.inspect.domain.Identifier
import java.util.*

class ServiceOrderID(private val value: String) : Identifier() {

    init {
        Objects.requireNonNull(value)
    }

    companion object {
        fun unique(): ServiceOrderID = from(UUID.randomUUID())
        fun from(anId: String): ServiceOrderID = ServiceOrderID(anId)
        fun from(anId: UUID): ServiceOrderID = ServiceOrderID(anId.toString().lowercase())
    }

    override fun getValue(): String = value

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ServiceOrderID

        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

}