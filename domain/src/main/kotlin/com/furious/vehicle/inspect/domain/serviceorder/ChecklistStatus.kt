package com.furious.vehicle.inspect.domain.serviceorder

enum class ChecklistStatus {
    PENDING,      // Checklist created but not signed
    NOT_OK,       // Vehicle issues found
    SIGNED;        // Customer signed off on the checklist

    companion object {
        fun from(value: String): ChecklistStatus {
            return ChecklistStatus.entries.firstOrNull { it.name.equals(value, ignoreCase = true) }
                ?: throw IllegalArgumentException("invalid checklist status: '$value'")
        }
    }
}