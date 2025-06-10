package com.furious.vehicle.inspect.application.customer.retrieve.list


import com.furious.vehicle.inspect.domain.customer.*
import com.furious.vehicle.inspect.domain.pagination.Pagination
import com.furious.vehicle.inspect.domain.pagination.SearchQuery
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.mockito.kotlin.any

class ListCustomersUseCaseTest {

    private val customerGateway: CustomerGateway = mock(CustomerGateway::class.java)
    private val useCase = DefaultListCustomersUseCase(customerGateway)

    @Test
    fun `should list all customers successfully`() {
        val customers = listOf(
            Customer.create(
                "John Doe",
                "(12)99999-9999",
                "john.doe@example.com",
                Document.create(DocumentType.CPF, "123456789"),
                Address.create("Street", "City", "State", "12345", "country")
            ), Customer.create(
                "Jane Smith",
                "(34)98888-8888",
                "jane.smith@example.com",
                Document.create(DocumentType.CPF, "987654321"),
                Address.create("Avenue", "Town", "Region", "54321", "country")
            )
        )
        val searchQuery = SearchQuery(0, 10, "", "", "")
        val pagination = Pagination(
            items = customers, currentPage = 0, perPage = 10, total = customers.size.toLong()
        )

        `when`(customerGateway.findAll(searchQuery)).thenReturn(pagination)

        val output = useCase.execute(searchQuery)

        assertNotNull(output)
        assertEquals(2, output.total)
        assertEquals("John Doe", output.items[0].name)
        assertEquals("Jane Smith", output.items[1].name)
        verify(customerGateway).findAll(any())
    }

    @Test
    fun `should return empty list when no customers exist`() {
        val searchQuery = SearchQuery(0, 10, "", "", "")
        val pagination = Pagination(
            items = emptyList<Customer>(), currentPage = 0, perPage = 10, total = 0
        )
        `when`(customerGateway.findAll(searchQuery)).thenReturn(pagination)

        val output = useCase.execute(searchQuery)

        assertNotNull(output)
        assertTrue(output.items.isEmpty())
        verify(customerGateway).findAll(any())
    }
}