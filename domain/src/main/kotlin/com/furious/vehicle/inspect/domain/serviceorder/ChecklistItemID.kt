package com.furious.vehicle.inspect.domain.serviceorder

import com.furious.vehicle.inspect.domain.Identifier

class ChecklistItemID private constructor(private val value: Long?) : Identifier() {

    companion object {
        fun create(): ChecklistItemID = from(0L)
        fun from(anId: Long): ChecklistItemID = ChecklistItemID(anId)
    }

    override fun getValue(): Any = value ?: 0L

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ChecklistItemID

        return value == other.value
    }

    override fun hashCode(): Int {
        return value?.hashCode() ?: 0
    }


}