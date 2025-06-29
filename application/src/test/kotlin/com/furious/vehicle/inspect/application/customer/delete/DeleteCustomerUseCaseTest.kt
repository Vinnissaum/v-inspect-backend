package com.furious.vehicle.inspect.application.customer.delete

import com.furious.vehicle.inspect.domain.customer.CustomerGateway
import com.furious.vehicle.inspect.domain.customer.CustomerID
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import kotlin.test.assertTrue

class DeleteCustomerUseCaseTest {

    private val customerGateway: CustomerGateway = mock(CustomerGateway::class.java)
    private val useCase = DefaultDeleteCustomerUseCase(customerGateway)

    @Test
    fun `should delete customer successfully`() {
        val customerId = CustomerID.from("123")

        doNothing().`when`(customerGateway).deleteById(customerId)

        useCase.execute(customerId.getValue())

        verify(customerGateway).deleteById(customerId)
    }

    @Test
    fun `given an invalid id when calls delete then should be ok`() {
        val customerId = CustomerID.from("invalid-id")

        doNothing().`when`(customerGateway).deleteById(customerId)

        assertDoesNotThrow {
            useCase.execute(customerId.getValue())
        }

        verify(customerGateway).deleteById(customerId)
    }

    @Test
    fun `should throw exception when gateway throws an exception`() {
        val customerId = CustomerID.from("123")

        doThrow(IllegalStateException("Gateway error")).`when`(customerGateway).deleteById(customerId)

        val exception = assertThrows<IllegalStateException> {
            useCase.execute(customerId.getValue())
        }

        assertTrue(exception.message!!.contains("Gateway error"))
        verify(customerGateway).deleteById(customerId)
    }
}