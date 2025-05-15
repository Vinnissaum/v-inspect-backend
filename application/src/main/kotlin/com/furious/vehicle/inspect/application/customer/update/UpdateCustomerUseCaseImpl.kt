package com.furious.vehicle.inspect.application.customer.update

import com.furious.vehicle.inspect.domain.customer.Customer
import com.furious.vehicle.inspect.domain.customer.CustomerGateway
import com.furious.vehicle.inspect.domain.exceptions.NotFoundException
import com.furious.vehicle.inspect.domain.exceptions.NotificationException
import com.furious.vehicle.inspect.domain.validation.handler.Notification

class UpdateCustomerUseCaseImpl(private val customerGateway: CustomerGateway) : UpdateCustomerUseCase() {

    override fun execute(input: UpdateCustomerCommand): UpdateCustomerOutput {
        val aCustomerId = input.id
        val actualCustomer = customerGateway.findById(aCustomerId) ?: //
        throw NotFoundException.with(Customer::class, aCustomerId)

        val notification = Notification.create()

        val aResult = notification.validate {
            actualCustomer.update( //
                input.name, //
                input.phone, //
                input.email, //
                input.document, //
                input.address //
            )
        }

        if (notification.hasError()) {
            throw NotificationException(
                "Could not update Aggregate Customer with ID ${aCustomerId.getValue()}", notification
            )
        }

        return UpdateCustomerOutput.from(
            customerGateway.update(aResult!!).getId()
        )
    }

}