package com.furious.vehicle.inspect.application.vehicle.add

import com.furious.vehicle.inspect.domain.customer.CustomerGateway
import com.furious.vehicle.inspect.domain.customer.CustomerID
import com.furious.vehicle.inspect.domain.exceptions.NotificationException
import com.furious.vehicle.inspect.domain.validation.AppError
import com.furious.vehicle.inspect.domain.validation.ValidationHandler
import com.furious.vehicle.inspect.domain.validation.handler.Notification
import com.furious.vehicle.inspect.domain.vehicle.VehicleGateway

class DefaultAddCustomerVehicleUseCase(
    private val vehicleGateway: VehicleGateway, private val customerGateway: CustomerGateway
) : AddCustomerVehicleUseCase() {

    override fun execute(input: AddCustomerVehicleCommand): AddCustomerVehicleOutput {
        val notification: Notification = Notification.create()

        customerExists(CustomerID.from(input.customerId)).also { notification.append(it) }

        val aVehicle = notification.validate {
            input.toDomain()
        }

        if (notification.hasError()) {
            throw NotificationException("Could not create an Aggregate Vehicle", notification)
        }

        return AddCustomerVehicleOutput.from(
            vehicleGateway.create(aVehicle!!).getId()
        )
    }

    private fun customerExists(customerId: CustomerID): ValidationHandler {
        val notification = Notification.create()

        customerGateway.findById(customerId) ?: return notification.append(
            AppError("Customer with ID ${customerId.getValue()} does not exist")
        )

        return notification
    }

}