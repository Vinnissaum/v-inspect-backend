package com.furious.vehicle.inspect.application.vehicle.retrieve.get

import com.furious.vehicle.inspect.domain.vehicle.Vehicle
import com.furious.vehicle.inspect.domain.vehicle.VehicleGateway
import com.furious.vehicle.inspect.domain.vehicle.VehicleID
import com.furious.vehicle.inspect.domain.exceptions.NotFoundException

class DefaultGetVehicleByIdUseCase(private val vehicleGateway: VehicleGateway) : GetVehicleByIdUseCase() {

    override fun execute(input: String): GetVehicleByIdOutput {
        val aVehicleId = VehicleID.from(input)

        return GetVehicleByIdOutput.from( //
            vehicleGateway.findById(aVehicleId) //
                ?: throw NotFoundException.with(Vehicle::class, aVehicleId) //
        )
    }

}