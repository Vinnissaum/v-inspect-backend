package com.furious.vehicle.inspect.application.vehicle.add

import com.furious.vehicle.inspect.domain.customer.CustomerGateway
import com.furious.vehicle.inspect.domain.exceptions.NotificationException
import com.furious.vehicle.inspect.domain.vehicle.UnitOfMeasurement
import com.furious.vehicle.inspect.domain.vehicle.VehicleGateway
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.AdditionalAnswers.returnsFirstArg
import org.mockito.Mockito.`when`
import org.mockito.kotlin.*
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


class AddCustomerVehicleUseCaseTest {

    private lateinit var useCase: DefaultAddCustomerVehicleUseCase
    private lateinit var vehicleGateway: VehicleGateway
    private lateinit var customerGateway: CustomerGateway

    @BeforeEach
    fun setUp() {
        vehicleGateway = mock()
        customerGateway = mock()
        useCase = DefaultAddCustomerVehicleUseCase(vehicleGateway, customerGateway)
    }


    @Test
    fun `given a valid command when calls add customer vehicle should return vehicle ID`() {
        // given
        val aCommand = AddCustomerVehicleCommand.with(
            customerId = "123",
            licensePlate = "ABC-1234",
            make = "Toyota",
            model = "Corolla",
            year = 2020,
            color = "Blue",
            mileageUnit = "Miles",
            mileage = 50000.0
        )

        // when
        `when`(customerGateway.findById(any())).thenReturn(mock())
        `when`(vehicleGateway.create(any())).thenAnswer(returnsFirstArg<AddCustomerVehicleOutput>())

        val anOutput = useCase.execute(aCommand)

        // then
        assertNotNull(anOutput)
        assertNotNull(anOutput.id)
        verify(vehicleGateway, times(1)).create(argThat {
            Objects.equals(this.customerID.getValue(), aCommand.customerId) //
                    && Objects.equals(this.color, aCommand.color) //
                    && Objects.equals(this.make, aCommand.make) //
                    && Objects.equals(this.model, aCommand.model) //
                    && Objects.equals(this.year, aCommand.year) //
                    && Objects.equals(this.licensePlate.value, aCommand.licensePlate) //
                    && Objects.equals(this.mileage?.value, aCommand.vehicleMileage) //
                    && Objects.equals(this.mileage?.unit, UnitOfMeasurement.from(aCommand.vehicleMileageUnit)) //
        })
    }

    @Test
    fun `given a command with a non-existent customer when calls add customer vehicle should return notification exception`() {
        // given
        val aCommand = AddCustomerVehicleCommand.with(
            customerId = "123",
            licensePlate = "ABC-1234",
            make = "Toyota",
            model = "Corolla",
            year = 2020,
            color = "Blue",
            mileageUnit = "Miles",
            mileage = 50000.0
        )

        // when
        `when`(customerGateway.findById(any())).thenReturn(null)

        // then
        val exception = kotlin.runCatching { useCase.execute(aCommand) }.exceptionOrNull()

        assert(exception is NotificationException)
        assertEquals(exception?.message, "Could not create an Aggregate Vehicle")

        verify(vehicleGateway, times(0)).create(any())
    }

    @Test
    fun `given an invalid command when calls add customer vehicle with empty model should return notification exception`() {
        // given
        val aCommand = AddCustomerVehicleCommand.with(
            customerId = "123",
            licensePlate = "ABC-1234",
            make = "Toyota",
            model = "",
            year = 2020,
            color = "Blue",
            mileageUnit = "Miles",
            mileage = 50000.0
        )

        // when
        `when`(customerGateway.findById(any())).thenReturn(mock())

        // then
        val exception = kotlin.runCatching { useCase.execute(aCommand) }.exceptionOrNull()

        assert(exception is NotificationException)
        assertEquals(exception?.message, "Could not create an Aggregate Vehicle")

        verify(vehicleGateway, times(0)).create(any())
    }

}