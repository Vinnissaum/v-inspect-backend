package com.furious.vehicle.inspect.application.customer.delete

import com.furious.vehicle.inspect.domain.customer.CustomerGateway
import com.furious.vehicle.inspect.domain.customer.CustomerID

class DefaultDeleteCustomerUseCase(private val customerGateway: CustomerGateway) : DeleteCustomerUseCase() {

    override fun execute(input: String) {
        customerGateway.deleteById(CustomerID.from(input))
    }

}