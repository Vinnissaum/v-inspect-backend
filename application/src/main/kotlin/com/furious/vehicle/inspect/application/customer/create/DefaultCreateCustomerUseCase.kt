package com.furious.vehicle.inspect.application.customer.create

import com.furious.vehicle.inspect.domain.customer.CustomerGateway
import com.furious.vehicle.inspect.domain.exceptions.NotificationException
import com.furious.vehicle.inspect.domain.validation.handler.Notification

class DefaultCreateCustomerUseCase(private val customerGateway: CustomerGateway) : CreateCustomerUseCase() {

    override fun execute(input: CreateCustomerCommand): CreateCustomerOutput {
        val notification: Notification = Notification.create()

        val aCustomer = notification.validate {
            input.toDomain()
        }

        if (notification.hasError()) {
            throw NotificationException("Could not create Aggregate Customer", notification)
        }

        return CreateCustomerOutput.from(
            customerGateway.create(aCustomer!!).getId()
        )
    }

}