package com.furious.vehicle.inspect.application.vehicle.update

import com.furious.vehicle.inspect.domain.vehicle.VehicleID

data class UpdateVehicleOutput(
    val id: String,
) {
    companion object {
        fun from(id: String): UpdateVehicleOutput = UpdateVehicleOutput(id)
        fun from(id: VehicleID): UpdateVehicleOutput = UpdateVehicleOutput(id.getValue())
    }
}