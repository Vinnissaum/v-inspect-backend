package com.furious.vehicle.inspect.application.vehicle.update

import com.furious.vehicle.inspect.domain.customer.CustomerID
import com.furious.vehicle.inspect.domain.vehicle.LicensePlate
import com.furious.vehicle.inspect.domain.vehicle.Mileage
import com.furious.vehicle.inspect.domain.vehicle.VehicleID

data class UpdateVehicleCommand(
    val id: VehicleID,
    val customerId: CustomerID,
    val make: String,
    val model: String,
    val year: Int,
    val color: String,
    val licensePlate: LicensePlate,
    val mileage: Mileage? = null,
) {
    companion object {
        fun with(
            id: VehicleID,
            customerId: CustomerID,
            make: String,
            model: String,
            year: Int,
            color: String,
            licensePlate: LicensePlate,
            mileage: Mileage? = null
        ): UpdateVehicleCommand {
            return UpdateVehicleCommand(
                id, customerId, make, model, year, color, licensePlate, mileage
            )
        }
    }
}