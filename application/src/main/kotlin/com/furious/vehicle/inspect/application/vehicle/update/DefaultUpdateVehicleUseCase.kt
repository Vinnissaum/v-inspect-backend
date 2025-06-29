package com.furious.vehicle.inspect.application.vehicle.update

import com.furious.vehicle.inspect.domain.exceptions.NotFoundException
import com.furious.vehicle.inspect.domain.exceptions.NotificationException
import com.furious.vehicle.inspect.domain.validation.handler.Notification
import com.furious.vehicle.inspect.domain.vehicle.Vehicle
import com.furious.vehicle.inspect.domain.vehicle.VehicleGateway

class DefaultUpdateVehicleUseCase(val vehicleGateway: VehicleGateway) : UpdateVehicleUseCase() {
    override fun execute(input: UpdateVehicleCommand): UpdateVehicleOutput {
        val aVehicleId = input.id

        val actualVehicle = vehicleGateway.findById(aVehicleId) ?: //
        throw NotFoundException.with(Vehicle::class, aVehicleId)

        val notification = Notification.create()

        val aResult = notification.validate {
            actualVehicle.update( //
                input.customerId, //
                input.make, //
                input.model, //
                input.year, //
                input.color, //
                input.licensePlate, //
                input.mileage //
            )
        }

        if (notification.hasError()) {
            throw NotificationException(
                "Could not update Aggregate Vehicle with ID ${aVehicleId.getValue()}", notification
            )
        }

        return UpdateVehicleOutput.from(aResult!!.getId())
    }
}