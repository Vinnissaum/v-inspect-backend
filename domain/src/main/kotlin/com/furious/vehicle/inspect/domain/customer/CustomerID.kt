package com.furious.vehicle.inspect.domain.customer

import com.furious.vehicle.inspect.domain.Identifier
import java.util.*


class CustomerID(private val value: String) : Identifier() {

    init {
        Objects.requireNonNull(value)
    }

    companion object Factory {
        fun unique(): CustomerID = from(UUID.randomUUID())
        fun from(anId: String): CustomerID = CustomerID(anId)
        fun from(anId: UUID): CustomerID = CustomerID(anId.toString().lowercase(Locale.getDefault()))
    }

    override fun getValue(): String = value

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CustomerID

        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

}