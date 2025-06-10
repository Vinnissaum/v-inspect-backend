package com.furious.vehicle.inspect.domain.vehicle

import com.furious.vehicle.inspect.domain.AggregateRoot
import com.furious.vehicle.inspect.domain.customer.CustomerID
import com.furious.vehicle.inspect.domain.exceptions.NotificationException
import com.furious.vehicle.inspect.domain.utils.InstantUtils
import com.furious.vehicle.inspect.domain.validation.ValidationHandler
import com.furious.vehicle.inspect.domain.validation.handler.Notification
import java.time.Instant

class Vehicle private constructor(
    anId: VehicleID,
    aCustomerId: CustomerID,
    aMake: String,
    aModel: String,
    aYear: Int,
    aColor: String,
    aLicensePlate: LicensePlate,
    aMileage: Mileage?,
    aCreatedAt: Instant,
    anUpdatedAt: Instant

) : AggregateRoot<VehicleID>(anId) {
    var customerID: CustomerID = aCustomerId
        private set
    var make: String = aMake
        private set
    var model: String = aModel
        private set
    var year: Int = aYear
        private set
    var color: String = aColor
        private set
    var licensePlate: LicensePlate = aLicensePlate
        private set
    var mileage: Mileage? = aMileage
        private set
    var createdAt: Instant = aCreatedAt
        private set
    var updatedAt: Instant = anUpdatedAt
        private set

    init {
        selfValidate() // Post construct validate
    }

    companion object {
        fun create(
            aCustomerId: CustomerID,
            aMake: String,
            aModel: String,
            aYear: Int,
            aColor: String,
            aLicensePlate: LicensePlate,
            aMileage: Mileage? = null
        ): Vehicle = Vehicle(
            VehicleID.unique(),
            aCustomerId,
            aMake,
            aModel,
            aYear,
            aColor,
            aLicensePlate,
            aMileage,
            InstantUtils.now(),
            InstantUtils.now()
        )

        fun with(aVehicle: Vehicle): Vehicle = Vehicle(
            aVehicle.id,
            aVehicle.customerID,
            aVehicle.make,
            aVehicle.model,
            aVehicle.year,
            aVehicle.color,
            aVehicle.licensePlate,
            aVehicle.mileage,
            aVehicle.createdAt,
            aVehicle.updatedAt
        )
    }

    fun update(
        aCustomerID: CustomerID,
        aMake: String,
        aModel: String,
        aYear: Int,
        aColor: String,
        aLicensePlate: LicensePlate,
        aMileage: Mileage? = null
    ): Vehicle {
        customerID = aCustomerID
        make = aMake
        model = aModel
        year = aYear
        color = aColor
        licensePlate = aLicensePlate
        mileage = aMileage
        updatedAt = InstantUtils.now()
        selfValidate(isUpdate = true)

        return this
    }

    override fun validate(handler: ValidationHandler) {
        VehicleValidator(this, handler).validate()
    }

    private fun selfValidate(isUpdate: Boolean = false) {
        val errorMessage = "Failed to ${if (isUpdate) "update" else "create"} an Entity Vehicle"
        val notification: Notification = Notification.create()
        this.validate(notification)

        if (notification.hasError()) {
            throw NotificationException(errorMessage, notification)
        }
    }

    fun getId(): VehicleID = id
}