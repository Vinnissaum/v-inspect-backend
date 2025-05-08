package com.furious.vehicle.inspect.domain.serviceorder

import com.furious.vehicle.inspect.domain.Identifier

class ServiceOrderItemID private constructor(private val value: Long?) : Identifier() {

    companion object {
        fun create(): ServiceOrderItemID = from(0L)
        fun from(anId: Long): ServiceOrderItemID = ServiceOrderItemID(anId)
    }

    override fun getValue(): Any = value ?: 0L

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ServiceOrderItemID

        return value == other.value
    }

    override fun hashCode(): Int {
        return value?.hashCode() ?: 0
    }
}