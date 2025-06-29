package com.furious.vehicle.inspect.application.vehicle.remove

import com.furious.vehicle.inspect.domain.vehicle.VehicleGateway
import com.furious.vehicle.inspect.domain.vehicle.VehicleID

class DefaultRemoveVehicleUseCase(val vehicleGateway: VehicleGateway) : RemoveVehicleUseCase() {

    override fun execute(input: String) {
        vehicleGateway.deleteById(VehicleID.from(input))
    }
}