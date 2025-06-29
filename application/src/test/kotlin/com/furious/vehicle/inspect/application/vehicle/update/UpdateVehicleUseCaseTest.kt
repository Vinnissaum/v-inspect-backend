package com.furious.vehicle.inspect.application.vehicle.update

import com.furious.vehicle.inspect.domain.customer.CustomerID
import com.furious.vehicle.inspect.domain.exceptions.NotFoundException
import com.furious.vehicle.inspect.domain.exceptions.NotificationException
import com.furious.vehicle.inspect.domain.vehicle.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class UpdateVehicleUseCaseTest {

    private lateinit var vehicleGateway: VehicleGateway
    private lateinit var useCase: DefaultUpdateVehicleUseCase

    @BeforeEach
    fun setUp() {
        vehicleGateway = mock()
        useCase = DefaultUpdateVehicleUseCase(vehicleGateway)
    }

    @Test
    fun `given a valid command when calls update vehicle should return vehicle ID`() {
        // given
        val customerId = CustomerID.unique()
        val existingVehicle = Vehicle.create(
            customerId,
            "Toyota",
            "Corolla",
            2020,
            "Blue",
            LicensePlate.create("ABC-1234"),
            Mileage.create(UnitOfMeasurement.MILES, 50000.0)
        )
        vehicleGateway.create(existingVehicle)

        val updateCommand = UpdateVehicleCommand.with(
            id = existingVehicle.getId(),
            customerId = CustomerID.unique(),
            make = "Honda",
            model = "Civic",
            year = 2021,
            color = "Red",
            licensePlate = LicensePlate.create("XYZ-9876"),
            mileage = Mileage.create(UnitOfMeasurement.MILES, 60000.0)
        )

        // when
        `when`(vehicleGateway.findById(existingVehicle.getId())).thenReturn(existingVehicle)
        val output = useCase.execute(updateCommand)

        // then
        assertNotNull(output)
        assertNotNull(output.id)

        val updatedVehicle = vehicleGateway.findById(existingVehicle.getId())!!
        assertEquals(updateCommand.make, updatedVehicle.make)
        assertEquals(updateCommand.model, updatedVehicle.model)
        assertEquals(updateCommand.year, updatedVehicle.year)
        assertEquals(updateCommand.color, updatedVehicle.color)
        assertEquals(updateCommand.licensePlate.value, updatedVehicle.licensePlate.value)
        assertEquals(updateCommand.mileage?.value, updatedVehicle.mileage?.value)
    }

    @Test
    fun `given a command with non-existent vehicle ID when calls update vehicle should throw NotFoundException`() {
        // given
        val nonExistentId = VehicleID.unique()
        val updateCommand = UpdateVehicleCommand.with(
            id = nonExistentId,
            customerId = CustomerID.unique(),
            make = "Honda",
            model = "Civic",
            year = 2021,
            color = "Red",
            licensePlate = LicensePlate.create("XYZ-9876"),
            mileage = Mileage.create(UnitOfMeasurement.MILES, 60000.0)
        )

        // when/then
        assertThrows<NotFoundException> {
            useCase.execute(updateCommand)
        }
    }

    @Test
    fun `given an invalid command when calls update vehicle should throw NotificationException`() {
        // given
        val customerId = CustomerID.unique()
        val existingVehicle = Vehicle.create(
            customerId,
            "Toyota",
            "Corolla",
            2020,
            "Blue",
            LicensePlate.create("ABC-1234"),
            Mileage.create(UnitOfMeasurement.MILES, 50000.0)
        )
        vehicleGateway.create(existingVehicle)

        val updateCommand = UpdateVehicleCommand.with(
            id = existingVehicle.getId(),
            customerId = CustomerID.unique(),
            make = "", // Invalid make
            model = "", // Invalid model
            year = -1, // Invalid year
            color = "Red",
            licensePlate = LicensePlate.create("XYZ-9876"),
            mileage = Mileage.create(UnitOfMeasurement.MILES, 60000.0)
        )

        // when/then
        `when`(vehicleGateway.findById(existingVehicle.getId())).thenReturn(existingVehicle)

        val exception = assertThrows<NotificationException> {
            useCase.execute(updateCommand)
        }

        assertEquals(
            "Could not update Aggregate Vehicle with ID ${existingVehicle.getId().getValue()}", exception.message
        )
    }

}
