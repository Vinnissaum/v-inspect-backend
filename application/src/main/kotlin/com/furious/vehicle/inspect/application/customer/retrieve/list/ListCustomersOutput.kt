package com.furious.vehicle.inspect.application.customer.retrieve.list

import com.furious.vehicle.inspect.domain.customer.Customer
import com.furious.vehicle.inspect.domain.customer.CustomerID
import com.furious.vehicle.inspect.domain.customer.Document
import java.time.Instant

data class ListCustomersOutput(
    val id: CustomerID,
    val name: String,
    val phone: String,
    val email: String,
    val document: Document,
    val createdAt: Instant
) {
    companion object {
        fun from(aCustomer: Customer): ListCustomersOutput {
            return ListCustomersOutput(
                id = aCustomer.getId(),
                name = aCustomer.name,
                phone = aCustomer.phone,
                email = aCustomer.email,
                document = aCustomer.document,
                createdAt = aCustomer.createdAt
            )
        }
    }
}
