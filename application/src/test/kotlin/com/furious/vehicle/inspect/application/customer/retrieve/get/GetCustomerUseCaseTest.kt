package com.furious.vehicle.inspect.application.customer.retrieve.get

import com.furious.vehicle.inspect.domain.customer.*
import com.furious.vehicle.inspect.domain.exceptions.NotFoundException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*

class GetCustomerUseCaseTest {

    private val customerGateway: CustomerGateway = mock(CustomerGateway::class.java)
    private val useCase = DefaultGetCustomerByIdUseCase(customerGateway)

    @Test
    fun `should retrieve customer successfully`() {
        val customer = Customer.create(
            "John Doe",
            "(12)99999-9999",
            "john.doe@example.com",
            Document.create(DocumentType.CPF, "123456789"),
            Address.create("Street", "City", "State", "12345", "country")
        )
        val customerId = customer.getId()

        `when`(customerGateway.findById(customerId)).thenReturn(customer)

        val output = useCase.execute(customerId.getValue())

        assertNotNull(output)
        assertEquals(customerId.getValue(), output.id.getValue())
        assertEquals("John Doe", output.name)
        verify(customerGateway).findById(customerId)
    }

    @Test
    fun `should throw NotFoundException when customer not found`() {
        val customerId = CustomerID.from("123")

        `when`(customerGateway.findById(customerId)).thenReturn(null)

        val exception = assertThrows<NotFoundException> {
            useCase.execute(customerId.getValue())
        }

        assertTrue(exception.message!!.contains("Customer with ID 123 was not found"))
        verify(customerGateway).findById(customerId)
    }
}