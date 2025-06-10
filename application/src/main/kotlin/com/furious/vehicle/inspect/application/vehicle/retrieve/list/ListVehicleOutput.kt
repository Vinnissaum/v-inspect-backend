package com.furious.vehicle.inspect.application.vehicle.retrieve.list

import com.furious.vehicle.inspect.domain.customer.CustomerID
import com.furious.vehicle.inspect.domain.vehicle.LicensePlate
import com.furious.vehicle.inspect.domain.vehicle.Mileage
import com.furious.vehicle.inspect.domain.vehicle.Vehicle
import com.furious.vehicle.inspect.domain.vehicle.VehicleID

data class ListVehicleOutput(
    val id: VehicleID,
    val customerId: CustomerID,
    val make: String,
    val model: String,
    val year: Int,
    val licensePlate: LicensePlate,
    val mileage: Mileage?,
) {
    companion object {
        fun from(aVehicle: Vehicle): ListVehicleOutput {
            return ListVehicleOutput(
                id = aVehicle.getId(),
                customerId = aVehicle.customerID,
                make = aVehicle.make,
                model = aVehicle.model,
                year = aVehicle.year,
                licensePlate = aVehicle.licensePlate,
                mileage = aVehicle.mileage
            )
        }
    }
}
