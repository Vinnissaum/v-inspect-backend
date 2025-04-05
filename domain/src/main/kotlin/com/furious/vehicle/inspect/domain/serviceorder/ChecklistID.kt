package com.furious.vehicle.inspect.domain.serviceorder

import com.furious.vehicle.inspect.domain.Identifier
import java.util.*

class ChecklistID(private val value: String) : Identifier() {

    init {
        Objects.requireNonNull(value)
    }

    companion object {
        fun unique(): ChecklistID = from(UUID.randomUUID())
        fun from(anId: String): ChecklistID = ChecklistID(anId)
        fun from(anId: UUID): ChecklistID = ChecklistID(anId.toString().lowercase())
    }

    override fun getValue(): String = value

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ChecklistID

        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }
}