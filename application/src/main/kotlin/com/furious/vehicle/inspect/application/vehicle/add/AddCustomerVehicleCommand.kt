package com.furious.vehicle.inspect.application.vehicle.add

import com.furious.vehicle.inspect.domain.customer.CustomerID
import com.furious.vehicle.inspect.domain.vehicle.LicensePlate
import com.furious.vehicle.inspect.domain.vehicle.Mileage
import com.furious.vehicle.inspect.domain.vehicle.UnitOfMeasurement
import com.furious.vehicle.inspect.domain.vehicle.Vehicle

data class AddCustomerVehicleCommand(
    val customerId: String,
    val make: String,
    val model: String,
    val year: Int,
    val color: String,
    val licensePlate: String,
    val vehicleMileage: Double = 0.0,
    val vehicleMileageUnit: String = "KM"
) {
    companion object {
        fun with(
            customerId: String,
            make: String,
            model: String,
            year: Int,
            color: String,
            licensePlate: String,
            mileage: Double = 0.0,
            mileageUnit: String = "KM"
        ) = AddCustomerVehicleCommand(
            customerId = customerId,
            make = make,
            model = model,
            year = year,
            color = color,
            licensePlate = licensePlate,
            vehicleMileage = mileage,
            vehicleMileageUnit = mileageUnit
        )
    }

    fun toDomain(): Vehicle = Vehicle.create( //
        CustomerID.from(customerId), //
        make, //
        model, //
        year, //
        color, //
        LicensePlate.create(licensePlate), //
        Mileage.create(UnitOfMeasurement.from(vehicleMileageUnit), vehicleMileage) //
    )
}
