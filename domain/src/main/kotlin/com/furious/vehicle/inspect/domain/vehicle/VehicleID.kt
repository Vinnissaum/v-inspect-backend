package com.furious.vehicle.inspect.domain.vehicle

import com.furious.vehicle.inspect.domain.Identifier
import java.util.*

class VehicleID private constructor(private val value: String) : Identifier() {

    init {
        Objects.requireNonNull(value)
    }

    companion object {
        fun unique(): VehicleID = from(UUID.randomUUID())
        fun from(anId: String): VehicleID = VehicleID(anId)
        fun from(anId: UUID): VehicleID = VehicleID(anId.toString().lowercase())
    }

    override fun getValue(): String = value

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VehicleID

        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

}