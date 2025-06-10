package com.furious.vehicle.inspect.application.vehicle.retrieve.get

import com.furious.vehicle.inspect.domain.customer.CustomerID
import com.furious.vehicle.inspect.domain.vehicle.LicensePlate
import com.furious.vehicle.inspect.domain.vehicle.Mileage
import com.furious.vehicle.inspect.domain.vehicle.Vehicle
import com.furious.vehicle.inspect.domain.vehicle.VehicleID
import java.time.Instant

data class GetVehicleByIdOutput(
    val id: VehicleID,
    val customerId: CustomerID,
    val make: String,
    val model: String,
    val year: Int,
    val licensePlate: LicensePlate,
    val mileage: Mileage?,
    val createdAt: Instant,
    val updatedAt: Instant
) {
    companion object {
        fun from(aVehicle: Vehicle): GetVehicleByIdOutput {
            return GetVehicleByIdOutput(
                id = aVehicle.getId(),
                customerId = aVehicle.customerID,
                make = aVehicle.make,
                model = aVehicle.model,
                year = aVehicle.year,
                licensePlate = aVehicle.licensePlate,
                mileage = aVehicle.mileage,
                createdAt = aVehicle.createdAt,
                updatedAt = aVehicle.updatedAt
            )
        }
    }
}