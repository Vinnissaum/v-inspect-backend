package com.furious.vehicle.inspect.domain.service

import com.furious.vehicle.inspect.domain.Identifier
import java.util.*

class ServiceID(private val value: String) : Identifier() {

    init {
        Objects.requireNonNull(value)
    }

    companion object {
        fun unique(): ServiceID = from(java.util.UUID.randomUUID())
        fun from(anId: String): ServiceID = ServiceID(anId)
        fun from(anId: java.util.UUID): ServiceID = ServiceID(anId.toString().lowercase())
    }

    override fun getValue(): String = value

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ServiceID

        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

}