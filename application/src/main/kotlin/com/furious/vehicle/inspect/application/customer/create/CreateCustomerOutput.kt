package com.furious.vehicle.inspect.application.customer.create

import com.furious.vehicle.inspect.domain.customer.CustomerID

data class CreateCustomerOutput(val id: String) {
    companion object {
        fun from(id: String): CreateCustomerOutput = CreateCustomerOutput(id)
        fun from(id: CustomerID): CreateCustomerOutput = CreateCustomerOutput(id.getValue())
    }
}