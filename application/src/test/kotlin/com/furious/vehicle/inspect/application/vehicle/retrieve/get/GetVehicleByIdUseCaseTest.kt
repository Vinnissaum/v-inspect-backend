package com.furious.vehicle.inspect.application.vehicle.retrieve.get

import com.furious.vehicle.inspect.domain.customer.CustomerID
import com.furious.vehicle.inspect.domain.exceptions.NotFoundException
import com.furious.vehicle.inspect.domain.vehicle.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class GetVehicleByIdUseCaseTest {

    private lateinit var useCase: DefaultGetVehicleByIdUseCase
    private lateinit var vehicleGateway: VehicleGateway

    @BeforeEach
    fun setUp() {
        vehicleGateway = mock()
        useCase = DefaultGetVehicleByIdUseCase(vehicleGateway)
    }

    @Test
    fun `given a valid vehicle ID when calls execute should return vehicle details`() {
        // Given
        val vehicle = Vehicle.create(
            aCustomerId = CustomerID.from("456"),
            aMake = "Toyota",
            aModel = "Corolla",
            aYear = 2020,
            aLicensePlate = LicensePlate.create("ABC-1234"),
            aMileage = Mileage.create(UnitOfMeasurement.KM, 50000.0),
            aColor = "Blue"
        )
        val vehicleId = vehicle.getId().getValue()
        // When
        `when`(vehicleGateway.findById(VehicleID.from(vehicleId))).thenReturn(vehicle)

        // Then
        val output = useCase.execute(vehicleId)
        assertNotNull(output)
        assertEquals(vehicleId, output.id.getValue())
        verify(vehicleGateway).findById(VehicleID.from(vehicleId))
    }

    @Test
    fun `given an invalid vehicle ID when calls execute should throw NotFoundException`() {
        // Given
        val vehicleId = "invalid-id"

        // When
        `when`(vehicleGateway.findById(VehicleID.from(vehicleId))).thenReturn(null)

        // Then
        val exception = assertThrows<NotFoundException> {
            useCase.execute(vehicleId)
        }
        assertEquals("Vehicle with ID $vehicleId was not found", exception.message)
        verify(vehicleGateway).findById(VehicleID.from(vehicleId))
    }
}