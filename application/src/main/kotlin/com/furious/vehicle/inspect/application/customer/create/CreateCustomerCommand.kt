package com.furious.vehicle.inspect.application.customer.create

import com.furious.vehicle.inspect.domain.customer.Address
import com.furious.vehicle.inspect.domain.customer.Customer
import com.furious.vehicle.inspect.domain.customer.Document

data class CreateCustomerCommand(
    val name: String, val phone: String, val email: String, val document: Document, val address: Address?
) {
    companion object {
        fun with(
            name: String, phone: String, email: String, document: Document, address: Address?
        ): CreateCustomerCommand {
            return CreateCustomerCommand(
                name, phone, email, document, address
            )
        }
    }

    fun toDomain(
    ): Customer = Customer.create( //
        name, //
        phone, //
        email, //
        document, //
        address //
    )
}