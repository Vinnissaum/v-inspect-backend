package com.furious.vehicle.inspect.application.customer.create

import com.furious.vehicle.inspect.domain.customer.*
import com.furious.vehicle.inspect.domain.exceptions.NotificationException
import com.furious.vehicle.inspect.domain.validation.AppError
import com.furious.vehicle.inspect.domain.validation.handler.Notification
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import org.mockito.kotlin.any

class CreateCustomerUseCaseTest {

    private val customerGateway: CustomerGateway = mock(CustomerGateway::class.java)
    private val useCase = DefaultCreateCustomerUseCase(customerGateway)

    @Test
    fun `should create customer successfully`() {
        val command = CreateCustomerCommand.with(
            "John Doe",
            "(12)99999-9999",
            "john.doe@example.com",
            Document.create(DocumentType.CPF, "123456789"),
            Address.create("Street", "City", "State", "12345", "country")
        )
        val customer = Customer.create(
            command.name,
            command.phone,
            command.email,
            command.document,
            command.address
        )

        `when`(customerGateway.create(any())).thenReturn(customer)

        val output = useCase.execute(command)

        assertNotNull(output)
        assertEquals(customer.getId().getValue(), output.id)
        verify(customerGateway).create(any())
    }

    @Test
    fun `should throw NotificationException when validation fails`() {
        val command = CreateCustomerCommand.with(
            "John Doe",
            "invalid-phone",
            "john.doe@example.com",
            Document.create(DocumentType.CPF, "123456789"),
            Address.create("Street", "City", "State", "12345", "country")
        )
        val notification = Notification.create()
        notification.append(AppError("Validation error"))

        val exception = assertThrows<NotificationException> {
            useCase.execute(command)
        }

        assertTrue(exception.message!!.contains("Could not create Aggregate Customer"))
        verify(customerGateway, times(0)).create(any())
    }
}