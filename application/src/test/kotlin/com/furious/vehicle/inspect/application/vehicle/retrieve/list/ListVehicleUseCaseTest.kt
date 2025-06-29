package com.furious.vehicle.inspect.application.vehicle.retrieve.list

import com.furious.vehicle.inspect.domain.customer.CustomerID
import com.furious.vehicle.inspect.domain.pagination.Pagination
import com.furious.vehicle.inspect.domain.pagination.SearchQuery
import com.furious.vehicle.inspect.domain.vehicle.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

class ListVehicleUseCaseTest {

    private lateinit var useCase: DefaultListVehicleUseCase
    private lateinit var vehicleGateway: VehicleGateway

    @BeforeEach
    fun setUp() {
        vehicleGateway = mock()
        useCase = DefaultListVehicleUseCase(vehicleGateway)
    }

    @Test
    fun `given a valid query when calls list vehicles should return vehicles paginated`() {
        // Given
        val vehicles = listOf(
            Vehicle.create(
                aCustomerId = CustomerID.from("123"),
                aMake = "Toyota",
                aModel = "Corolla",
                aYear = 2020,
                aLicensePlate = LicensePlate.create("XYZ-1234"),
                aMileage = Mileage.create(UnitOfMeasurement.KM, 30000.0),
                aColor = "Red"
            ), Vehicle.create(
                aCustomerId = CustomerID.from("456"),
                aMake = "Honda",
                aModel = "Civic",
                aYear = 2019,
                aLicensePlate = LicensePlate.create("ABC-5678"),
                aMileage = Mileage.create(UnitOfMeasurement.KM, 25000.0),
                aColor = "Blue"
            )
        )
        val expectedPage = 0
        val expectedPerPage = 10
        val expectedTerms = "A"
        val expectedSort = "createdAt"
        val expectedDirection = "asc"
        val expectedTotal = 2L

        val query = SearchQuery(
            page = expectedPage,
            perPage = expectedPerPage,
            terms = expectedTerms,
            sort = expectedSort,
            direction = expectedDirection
        )
        `when`(vehicleGateway.findAll(query)).thenReturn(
            Pagination(
                items = vehicles,
                total = expectedTotal,
                currentPage = expectedPage,
                perPage = expectedPerPage,
            )
        )

        // When
        val output = useCase.execute(query)
        // Then

        assertAll(
            { assertEquals(expectedPage, output.currentPage) },
            { assertEquals(expectedPerPage, output.perPage) },
            { assertEquals(expectedTotal, output.total) },
            { assertEquals(vehicles.size, output.items.size) },
            { assertEquals(vehicles[0].getId().getValue(), output.items[0].id.getValue()) },
            { assertEquals(vehicles[1].getId().getValue(), output.items[1].id.getValue()) }
        )
    }

    @Test
    fun `given a valid query when calls list vehicles should return empty pagination`() {
        // Given
        val expectedPage = 0
        val expectedPerPage = 10
        val expectedTerms = "A"
        val expectedSort = "createdAt"
        val expectedDirection = "asc"
        val expectedTotal = 0L

        val query = SearchQuery(
            page = expectedPage,
            perPage = expectedPerPage,
            terms = expectedTerms,
            sort = expectedSort,
            direction = expectedDirection
        )
        `when`(vehicleGateway.findAll(query)).thenReturn(
            Pagination(
                items = emptyList(),
                total = expectedTotal,
                currentPage = expectedPage,
                perPage = expectedPerPage,
            )
        )

        // When
        val output = useCase.execute(query)

        // Then
        assertAll(
            { assertEquals(expectedPage, output.currentPage) },
            { assertEquals(expectedPerPage, output.perPage) },
            { assertEquals(expectedTotal, output.total) },
            { assertEquals(0, output.items.size) }
        )
    }

    @Test
    fun `given a valid query when calls list vehicles and gateway throws any error should throw an exception`() {
        // Given
        val expectedPage = 0
        val expectedPerPage = 10
        val expectedTerms = "A"
        val expectedSort = "createdAt"
        val expectedDirection = "asc"

        val query = SearchQuery(
            page = expectedPage,
            perPage = expectedPerPage,
            terms = expectedTerms,
            sort = expectedSort,
            direction = expectedDirection
        )
        `when`(vehicleGateway.findAll(query)).thenThrow(RuntimeException("Database error"))

        // When & Then
        try {
            useCase.execute(query)
        } catch (e: Exception) {
            assertEquals("Database error", e.message)
        }
    }

}