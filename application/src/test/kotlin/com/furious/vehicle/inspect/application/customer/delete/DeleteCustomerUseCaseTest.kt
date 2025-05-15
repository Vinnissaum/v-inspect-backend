package com.furious.vehicle.inspect.application.customer.delete

import com.furious.vehicle.inspect.domain.customer.Customer
import com.furious.vehicle.inspect.domain.customer.CustomerGateway
import com.furious.vehicle.inspect.domain.customer.CustomerID
import com.furious.vehicle.inspect.domain.exceptions.NotFoundException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import kotlin.test.assertTrue

class DeleteCustomerUseCaseTest {

    private val customerGateway: CustomerGateway = mock(CustomerGateway::class.java)
    private val useCase = DeleteCustomerUseCaseImpl(customerGateway)

    @Test
    fun `should delete customer successfully`() {
        val customerId = CustomerID.from("123")

        doNothing().`when`(customerGateway).deleteById(customerId)

        useCase.execute(customerId.getValue())

        verify(customerGateway).deleteById(customerId)
    }

    @Test
    fun `should throw NotFoundException when customer does not exist`() {
        val customerId = CustomerID.from("123")

        doThrow(NotFoundException.with(Customer::class, customerId))
            .`when`(customerGateway).deleteById(customerId)

        val exception = assertThrows<NotFoundException> {
            useCase.execute(customerId.getValue())
        }

        assertTrue(exception.message!!.contains("Customer with ID 123 was not found"))
        verify(customerGateway).deleteById(customerId)
    }
}