package com.furious.vehicle.inspect.domain.serviceorder

enum class ServiceOrderStatus {
    PENDING, IN_PROGRESS, COMPLETED, CANCELLED;

    companion object {
        fun from(value: String): ServiceOrderStatus {
            return entries.firstOrNull { it.name.equals(value, ignoreCase = true) }
                ?: throw IllegalArgumentException("invalid service order status: '$value'")
        }
    }
}