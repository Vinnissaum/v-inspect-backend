package com.furious.vehicle.inspect.application.customer.update

import com.furious.vehicle.inspect.domain.customer.CustomerID

data class UpdateCustomerOutput(
    val id: String,
) {
    companion object {
        fun from(id: String): UpdateCustomerOutput = UpdateCustomerOutput(id)
        fun from(id: CustomerID): UpdateCustomerOutput = UpdateCustomerOutput(id.getValue())
    }
}