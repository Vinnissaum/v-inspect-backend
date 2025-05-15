package com.furious.vehicle.inspect.application.customer.update

import com.furious.vehicle.inspect.domain.customer.*
import com.furious.vehicle.inspect.domain.exceptions.NotFoundException
import com.furious.vehicle.inspect.domain.exceptions.NotificationException
import com.furious.vehicle.inspect.domain.validation.AppError
import com.furious.vehicle.inspect.domain.validation.handler.Notification
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*

class UpdateCustomerUseCaseTest {

    private val customerGateway: CustomerGateway = mock(CustomerGateway::class.java)
    private val useCase = UpdateCustomerUseCaseImpl(customerGateway)

    @Test
    fun `should update customer successfully`() {
        val customer = Customer.create(
            "John Doe",
            "(12)99999-9999",
            "admin@email.com",
            Document.create(DocumentType.CPF, "123456789"),
            Address.create("Street", "City", "State", "12345", "country")
        )
        val customerId = customer.getId()
        val command = UpdateCustomerCommand.with(
            customerId,
            "John Doe",
            "(12)99999-9999",
            "john.doe@example.com",
            Document.create(DocumentType.CPF, "123456789"),
            Address.create("Street", "City", "State", "12345", "country")
        )

        `when`(customerGateway.findById(customerId)).thenReturn(customer)
        `when`(customerGateway.update(customer)).thenReturn(customer)

        val output = useCase.execute(command)

        assertNotNull(output)
        assertEquals(customerId.getValue(), output.id)
        assertEquals("John Doe", customer.name)
        verify(customerGateway).findById(customerId)
        verify(customerGateway).update(customer)
    }

    @Test
    fun `should throw NotFoundException when customer not found`() {
        val customerId = CustomerID.from("123")
        val command = UpdateCustomerCommand.with(
            customerId,
            "John Doe",
            "(12)9999-9999",
            "john.doe@example.com",
            Document.create(DocumentType.CPF, "123456789"),
            Address.create("Street", "City", "State", "12345", "country")
        )

        `when`(customerGateway.findById(customerId)).thenReturn(null)

        val exception = assertThrows<NotFoundException> {
            useCase.execute(command)
        }

        assertEquals("Customer with ID 123 was not found", exception.message)
        verify(customerGateway).findById(customerId)
        verifyNoMoreInteractions(customerGateway)
    }

    @Test
    fun `should throw NotificationException when validation fails`() {
        val customerId = CustomerID.from("123")
        val customer = Customer.create(
            "John Doe",
            "(12)9999-9999",
            "admin@email.com",
            Document.create(DocumentType.CPF, "123456789"),
            Address.create("Street", "City", "State", "12345", "country")
        )
        val command = UpdateCustomerCommand.with(
            customerId,
            "John Doe",
            "xx", // Incorrect phone format
            "john.doe@example.com",
            Document.create(DocumentType.CPF, "123456789"),
            Address.create("Street", "City", "State", "12345", "country")
        )
        val notification = Notification.create()
        notification.append(AppError("Validation error"))

        `when`(customerGateway.findById(customerId)).thenReturn(customer)

        val exception = assertThrows<NotificationException> {
            useCase.execute(command)
        }

        assertTrue(exception.message!!.contains("Could not update Aggregate Customer with ID"))
        verify(customerGateway).findById(customerId)
        verifyNoMoreInteractions(customerGateway)
    }
}