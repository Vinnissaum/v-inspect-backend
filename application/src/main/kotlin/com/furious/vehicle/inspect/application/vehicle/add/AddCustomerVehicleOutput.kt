package com.furious.vehicle.inspect.application.vehicle.add

import com.furious.vehicle.inspect.domain.vehicle.VehicleID

data class AddCustomerVehicleOutput(val id: String) {
    companion object {
        fun from(id: String): AddCustomerVehicleOutput = AddCustomerVehicleOutput(id)
        fun from(id: VehicleID): AddCustomerVehicleOutput = AddCustomerVehicleOutput(id.getValue())
    }
}
