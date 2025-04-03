package com.furious.vehicle.inspect.domain.serviceorder

import com.furious.vehicle.inspect.domain.Identifier

class ChecklistID(private val value: Long? = 0L) :
    Identifier() { // This ID will be auto incremented in the DB, so it's initialized with 0

    companion object {
        fun from(anId: Long): ChecklistID = ChecklistID(anId)
    }

    override fun getValue(): Long = value ?: 0L

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