package com.furious.vehicle.inspect.application.customer.retrieve.get

import com.furious.vehicle.inspect.domain.customer.Address
import com.furious.vehicle.inspect.domain.customer.Customer
import com.furious.vehicle.inspect.domain.customer.CustomerID
import com.furious.vehicle.inspect.domain.customer.Document
import java.time.Instant

data class GetCustomerByIdOutput(
    val id: CustomerID,
    val name: String,
    val phone: String,
    val email: String,
    val document: Document,
    val address: Address?,
    val createdAt: Instant,
    val updatedAt: Instant
) {
    companion object {
        fun from(aCustomer: Customer): GetCustomerByIdOutput {
            return GetCustomerByIdOutput(
                id = aCustomer.getId(),
                name = aCustomer.name,
                phone = aCustomer.phone,
                email = aCustomer.email,
                document = aCustomer.document,
                address = aCustomer.address,
                createdAt = aCustomer.createdAt,
                updatedAt = aCustomer.updatedAt
            )
        }
    }

}
