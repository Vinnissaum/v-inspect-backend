package com.furious.vehicle.inspect.domain.servicecategory

import com.furious.vehicle.inspect.domain.Identifier
import java.util.*

class ServiceCategoryID(private val value: String) : Identifier() {

    init {
        Objects.requireNonNull(value)
    }

    companion object {
        fun unique(): ServiceCategoryID = from(UUID.randomUUID())
        fun from(anId: String): ServiceCategoryID = ServiceCategoryID(anId)
        fun from(anId: UUID): ServiceCategoryID = ServiceCategoryID(anId.toString().lowercase())
    }

    override fun getValue(): String = value

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ServiceCategoryID

        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

}
