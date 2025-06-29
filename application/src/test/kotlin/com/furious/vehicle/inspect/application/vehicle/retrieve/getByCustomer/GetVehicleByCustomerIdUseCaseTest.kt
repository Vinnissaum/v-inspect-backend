package com.furious.vehicle.inspect.application.vehicle.retrieve.getByCustomer

import com.furious.vehicle.inspect.domain.customer.CustomerID
import com.furious.vehicle.inspect.domain.vehicle.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class GetVehicleByCustomerIdUseCaseTest {

    private lateinit var useCase: DefaultGetVehicleByCustomerIdUseCase
    private lateinit var vehicleGateway: VehicleGateway

    @BeforeEach
    fun setUp() {
        vehicleGateway = mock()
        useCase = DefaultGetVehicleByCustomerIdUseCase(vehicleGateway)
    }

    @Test
    fun `given a valid customer ID when calls execute should return vehicles by customer ID`() {
        // Given
        val customerId = CustomerID.from("123")
        val vehicle = Vehicle.create(
            aCustomerId = customerId,
            aMake = "Toyota",
            aModel = "Corolla",
            aYear = 2020,
            aLicensePlate = LicensePlate.create("ABC-1234"),
            aMileage = Mileage.create(UnitOfMeasurement.KM, 50000.0),
            aColor = "Blue"
        )
        val vehicleId = vehicle.getId().getValue()
        // When
        `when`(vehicleGateway.findByCustomerId(customerId)).thenReturn(listOf(vehicle))

        // Then
        val output = useCase.execute(customerId.getValue())
        assertNotNull(output)
        assertEquals(vehicleId, output[0].id.getValue())
        verify(vehicleGateway).findByCustomerId(customerId)
    }

    @Test
    fun `given an invalid customer ID when calls execute should return empty list`() {
        // Given
        val customerId = CustomerID.from("invalid-id")

        // When
        `when`(vehicleGateway.findByCustomerId(customerId)).thenReturn(emptyList())

        // Then
        val output = useCase.execute(customerId.getValue())
        assertNotNull(output)
        assertEquals(0, output.size)
        verify(vehicleGateway).findByCustomerId(customerId)
    }

    @Test
    fun `given a customer ID with no vehicles when calls execute should return empty list`() {
        // Given
        val customerId = CustomerID.from("456")

        // When
        `when`(vehicleGateway.findByCustomerId(customerId)).thenReturn(emptyList())

        // Then
        val output = useCase.execute(customerId.getValue())
        assertNotNull(output)
        assertEquals(0, output.size)
        verify(vehicleGateway).findByCustomerId(customerId)
    }

    @Test
    fun `given a customer ID with multiple vehicles when calls execute should return all vehicles`() {
        // Given
        val customerId = CustomerID.from("789")
        val vehicle1 = Vehicle.create(
            aCustomerId = customerId,
            aMake = "Honda",
            aModel = "Civic",
            aYear = 2019,
            aLicensePlate = LicensePlate.create("XYZ-5678"),
            aMileage = Mileage.create(UnitOfMeasurement.KM, 30000.0),
            aColor = "Red"
        )
        val vehicle2 = Vehicle.create(
            aCustomerId = customerId,
            aMake = "Ford",
            aModel = "Focus",
            aYear = 2021,
            aLicensePlate = LicensePlate.create("LMN-1234"),
            aMileage = Mileage.create(UnitOfMeasurement.KM, 15000.0),
            aColor = "Green"
        )
        // When
        `when`(vehicleGateway.findByCustomerId(customerId)).thenReturn(listOf(vehicle1, vehicle2))

        // Then
        val output = useCase.execute(customerId.getValue())
        assertNotNull(output)
        assertEquals(2, output.size)
        assertEquals(vehicle1.getId().getValue(), output[0].id.getValue())
        assertEquals(vehicle2.getId().getValue(), output[1].id.getValue())
        verify(vehicleGateway).findByCustomerId(customerId)
    }

    @Test
    fun `given a valid customer ID when calls get by customer ID and gateway throws exception should propagate exception`() {
        // Given
        val customerId = CustomerID.from("123")
        val exceptionMessage = "Database error"

        // When
        `when`(vehicleGateway.findByCustomerId(customerId)).thenThrow(RuntimeException(exceptionMessage))

        // Then
        try {
            useCase.execute(customerId.getValue())
        } catch (e: RuntimeException) {
            assertEquals(exceptionMessage, e.message)
        }
        verify(vehicleGateway).findByCustomerId(customerId)
    }

}