package com.furious.vehicle.inspect.application.customer.retrieve.get

import com.furious.vehicle.inspect.domain.customer.Customer
import com.furious.vehicle.inspect.domain.customer.CustomerGateway
import com.furious.vehicle.inspect.domain.customer.CustomerID
import com.furious.vehicle.inspect.domain.exceptions.NotFoundException

class DefaultGetCustomerByIdUseCase(private val customerGateway: CustomerGateway) : GetCustomerByIdUseCase() {

    override fun execute(input: String): GetCustomerByIdOutput {
        val aCustomerId = CustomerID.from(input)

        return GetCustomerByIdOutput.from(
            customerGateway.findById(aCustomerId) //
                ?: throw NotFoundException.with(Customer::class, aCustomerId)
        )
    }

}