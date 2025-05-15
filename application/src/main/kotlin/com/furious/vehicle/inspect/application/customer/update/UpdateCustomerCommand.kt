package com.furious.vehicle.inspect.application.customer.update

import com.furious.vehicle.inspect.domain.customer.Address
import com.furious.vehicle.inspect.domain.customer.CustomerID
import com.furious.vehicle.inspect.domain.customer.Document

data class UpdateCustomerCommand(
    val id: CustomerID,
    val name: String,
    val phone: String,
    val email: String,
    val document: Document,
    val address: Address?
) {
    companion object {
        fun with(
            id: CustomerID, name: String, phone: String, email: String, document: Document, address: Address?
        ): UpdateCustomerCommand {
            return UpdateCustomerCommand(
                id, name, phone, email, document, address
            )
        }
    }
}