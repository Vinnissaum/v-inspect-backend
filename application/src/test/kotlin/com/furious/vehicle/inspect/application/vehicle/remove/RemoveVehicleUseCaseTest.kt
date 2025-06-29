package com.furious.vehicle.inspect.application.vehicle.remove

import com.furious.vehicle.inspect.domain.vehicle.VehicleGateway
import com.furious.vehicle.inspect.domain.vehicle.VehicleID
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import org.mockito.kotlin.mock
import kotlin.test.assertTrue

class RemoveVehicleUseCaseTest {

    private lateinit var useCase: DefaultRemoveVehicleUseCase
    private lateinit var vehicleGateway: VehicleGateway

    @BeforeEach
    fun setUp() {
        vehicleGateway = mock()
        useCase = DefaultRemoveVehicleUseCase(vehicleGateway)
    }

    @Test
    fun `should delete vehicle successfully`() {
        val customerId = VehicleID.from("123")

        doNothing().`when`(vehicleGateway).deleteById(customerId)

        useCase.execute(customerId.getValue())

        verify(vehicleGateway).deleteById(customerId)
    }

    @Test
    fun `given an invalid id when calls delete then should be ok`() {
        val customerId = VehicleID.from("invalid-id")

        doNothing().`when`(vehicleGateway).deleteById(customerId)

        assertDoesNotThrow {
            useCase.execute(customerId.getValue())
        }

        verify(vehicleGateway).deleteById(customerId)
    }

    @Test
    fun `should throw exception when gateway throws an exception`() {
        val customerId = VehicleID.from("123")

        doThrow(IllegalStateException("Gateway error")).`when`(vehicleGateway).deleteById(customerId)

        val exception = assertThrows<IllegalStateException> {
            useCase.execute(customerId.getValue())
        }

        assertTrue(exception.message!!.contains("Gateway error"))
        verify(vehicleGateway).deleteById(customerId)
    }
}