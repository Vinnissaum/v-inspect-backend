package com.furious.vehicle.inspect.domain.serviceorder

import com.furious.vehicle.inspect.domain.customer.CustomerID
import com.furious.vehicle.inspect.domain.vehicle.VehicleID
import com.furious.vehicle.inspect.domain.service.Service
import com.furious.vehicle.inspect.domain.servicecategory.ServiceCategoryID
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class ServiceOrderTest {

    @Test
    fun `given a valid params when call create then instantiate a ServiceOrder`() {
        val expectedCustomer = CustomerID.from("123")
        val expectedVehicle = VehicleID.from("123")

        val aServiceOrder = ServiceOrder.create(
            expectedCustomer, expectedVehicle
        )

        assertAll(
            "Service Order validation",
            { assertNotNull(aServiceOrder.getId()) },
            { assertEquals(expectedCustomer, aServiceOrder.customer) },
            { assertEquals(expectedVehicle, aServiceOrder.vehicle) },
            { assertEquals(ServiceOrderStatus.PENDING, aServiceOrder.status) },
            { assertTrue(aServiceOrder.items.isEmpty()) },
            { assertTrue(aServiceOrder.total == BigDecimal.ZERO) },
            { assertNull(aServiceOrder.checkList) },
            { assertNotNull(aServiceOrder.createdAt) },
            { assertNotNull(aServiceOrder.updatedAt) },
            { assertNull(aServiceOrder.completedAt) })
    }

    @Test
    fun `given a valid params with a checklist when call create then instantiate a ServiceOrder`() {
        val expectedCustomer = CustomerID.from("123")
        val expectedVehicle = VehicleID.from("123")
        val expectedChecklist = ChecklistID.from("123")

        val aServiceOrder = ServiceOrder.create(
            expectedCustomer, expectedVehicle, expectedChecklist
        )

        assertAll(
            "Service Order validation",
            { assertNotNull(aServiceOrder.getId()) },
            { assertEquals(expectedCustomer, aServiceOrder.customer) },
            { assertEquals(expectedVehicle, aServiceOrder.vehicle) },
            { assertEquals(ServiceOrderStatus.PENDING, aServiceOrder.status) },
            { assertTrue(aServiceOrder.items.isEmpty()) },
            { assertTrue(aServiceOrder.total == BigDecimal.ZERO) },
            { assertEquals(expectedChecklist, aServiceOrder.checkList) },
            { assertNotNull(aServiceOrder.createdAt) },
            { assertNotNull(aServiceOrder.updatedAt) },
            { assertNull(aServiceOrder.completedAt) })
    }

    @Test
    fun `given a ServiceOrder when call calculateTotal without items should return the ServiceOrder with zero total`() {
        val expectedCustomer = CustomerID.from("123")
        val expectedVehicle = VehicleID.from("123")

        val aServiceOrder = ServiceOrder.create(
            expectedCustomer, expectedVehicle
        )

        val actualTotal = aServiceOrder.total

        aServiceOrder.calculateTotal()

        assertAll(
            "Service Order validation",
            { assertNotNull(aServiceOrder.getId()) },
            { assertEquals(expectedCustomer, aServiceOrder.customer) },
            { assertEquals(expectedVehicle, aServiceOrder.vehicle) },
            { assertEquals(ServiceOrderStatus.PENDING, aServiceOrder.status) },
            { assertTrue(aServiceOrder.items.isEmpty()) },
            { assertTrue(aServiceOrder.total == BigDecimal.ZERO) },
            { assertEquals(actualTotal, aServiceOrder.total) },
            { assertNull(aServiceOrder.checkList) },
            { assertNotNull(aServiceOrder.createdAt) },
            { assertNotNull(aServiceOrder.updatedAt) },
            { assertNull(aServiceOrder.completedAt) })
    }

    @Test
    fun `given a ServiceOrder when call addItem should return the ServiceOrder with recalculated total`() {
        val expectedCustomer = CustomerID.from("123")
        val expectedVehicle = VehicleID.from("123")
        val expectedService = Service.create(
            "description", ServiceCategoryID.from("123"), BigDecimal("10.00")
        )
        val expectedServiceOrderItem = ServiceOrderItem.create(
            ServiceOrderID.from("123"), expectedService, aQuantity = 2
        )
        val expectedItemsCount = 1

        val aServiceOrder = ServiceOrder.create(
            expectedCustomer, expectedVehicle
        )
        val actualTotal = aServiceOrder.total

        aServiceOrder.addItem(expectedServiceOrderItem)

        assertAll(
            "Service Order validation",
            { assertNotNull(aServiceOrder.getId()) },
            { assertEquals(expectedCustomer, aServiceOrder.customer) },
            { assertEquals(expectedVehicle, aServiceOrder.vehicle) },
            { assertEquals(ServiceOrderStatus.PENDING, aServiceOrder.status) },
            { assertEquals(expectedItemsCount, aServiceOrder.items.size) },
            { assertEquals(BigDecimal("20.00"), aServiceOrder.total) },
            { assertTrue(actualTotal < aServiceOrder.total) },
            { assertNull(aServiceOrder.checkList) },
            { assertNotNull(aServiceOrder.createdAt) },
            { assertNotNull(aServiceOrder.updatedAt) },
            { assertNull(aServiceOrder.completedAt) })
    }

    @Test
    fun `given a ServiceOrder when call removeItem should return the ServiceOrder with recalculated total`() {
        val expectedCustomer = CustomerID.from("123")
        val expectedVehicle = VehicleID.from("123")
        val expectedService = Service.create(
            "description", ServiceCategoryID.from("123"), BigDecimal("10.00")
        )
        val expectedServiceOrderItem = ServiceOrderItem.create(
            ServiceOrderID.from("123"), expectedService, aQuantity = 2
        )
        val expectedItemsCount = 0

        val aServiceOrder = ServiceOrder.create(
            expectedCustomer, expectedVehicle
        )

        aServiceOrder.addItem(expectedServiceOrderItem)
        val actualTotal = aServiceOrder.total

        aServiceOrder.removeItem(expectedServiceOrderItem.getId())

        assertAll(
            "Service Order validation",
            { assertNotNull(aServiceOrder.getId()) },
            { assertEquals(expectedCustomer, aServiceOrder.customer) },
            { assertEquals(expectedVehicle, aServiceOrder.vehicle) },
            { assertEquals(ServiceOrderStatus.PENDING, aServiceOrder.status) },
            { assertEquals(expectedItemsCount, aServiceOrder.items.size) },
            { assertEquals(BigDecimal.ZERO, aServiceOrder.total) },
            { assertTrue(actualTotal > aServiceOrder.total) },
            { assertNull(aServiceOrder.checkList) },
            { assertNotNull(aServiceOrder.createdAt) },
            { assertNotNull(aServiceOrder.updatedAt) },
            { assertNull(aServiceOrder.completedAt) })
    }

    @Test
    fun `given a ServiceOrder when call removeItem with invalid id should return the ServiceOrder with no changes`() {
        val expectedCustomer = CustomerID.from("123")
        val expectedVehicle = VehicleID.from("123")
        val expectedService = Service.create(
            "description", ServiceCategoryID.from("123"), BigDecimal("10.00")
        )
        val expectedServiceOrderItem = ServiceOrderItem.create(
            ServiceOrderID.from("123"), expectedService, aQuantity = 2
        )
        val expectedItemsCount = 1

        val aServiceOrder = ServiceOrder.create(
            expectedCustomer, expectedVehicle
        )

        aServiceOrder.addItem(expectedServiceOrderItem)
        val actualTotal = aServiceOrder.total

        aServiceOrder.removeItem(ServiceOrderItemID.from(1L))

        assertAll(
            "Service Order validation",
            { assertNotNull(aServiceOrder.getId()) },
            { assertEquals(expectedCustomer, aServiceOrder.customer) },
            { assertEquals(expectedVehicle, aServiceOrder.vehicle) },
            { assertEquals(ServiceOrderStatus.PENDING, aServiceOrder.status) },
            { assertEquals(expectedItemsCount, aServiceOrder.items.size) },
            { assertEquals(BigDecimal("20.00"), aServiceOrder.total) },
            { assertTrue(actualTotal == aServiceOrder.total) },
            { assertNull(aServiceOrder.checkList) },
            { assertNotNull(aServiceOrder.createdAt) },
            { assertNotNull(aServiceOrder.updatedAt) },
            { assertNull(aServiceOrder.completedAt) })
    }

    @Test
    fun `given a ServiceOrder when call progress should return the ServiceOrder with status and updated at changed`() {
        val expectedCustomer = CustomerID.from("123")
        val expectedVehicle = VehicleID.from("123")
        val expectedService = Service.create(
            "description", ServiceCategoryID.from("123"), BigDecimal("10.00")
        )
        val expectedServiceOrderItem = ServiceOrderItem.create(
            ServiceOrderID.from("123"), expectedService, aQuantity = 2
        )
        val expectedItemsCount = 1

        val aServiceOrder = ServiceOrder.create(
            expectedCustomer, expectedVehicle
        )
        val actualUpdatedAt = aServiceOrder.updatedAt
        val actualTotal = aServiceOrder.total

        Thread.sleep(1) // To ensure the updatedAt is different
        aServiceOrder.addItem(expectedServiceOrderItem)
        aServiceOrder.progress()

        assertAll(
            "Service Order validation",
            { assertNotNull(aServiceOrder.getId()) },
            { assertEquals(expectedCustomer, aServiceOrder.customer) },
            { assertEquals(expectedVehicle, aServiceOrder.vehicle) },
            { assertEquals(ServiceOrderStatus.IN_PROGRESS, aServiceOrder.status) },
            { assertEquals(expectedItemsCount, aServiceOrder.items.size) },
            { assertEquals(BigDecimal("20.00"), aServiceOrder.total) },
            { assertTrue(actualTotal < aServiceOrder.total) },
            { assertNull(aServiceOrder.checkList) },
            { assertNotNull(aServiceOrder.createdAt) },
            { assertNotNull(aServiceOrder.updatedAt) },
            { assertTrue(actualUpdatedAt.isBefore(aServiceOrder.updatedAt)) },
            { assertNull(aServiceOrder.completedAt) })
    }

    @Test
    fun `given a ServiceOrder when call complete should return the ServiceOrder with status and completedAt changed`() {
        val expectedCustomer = CustomerID.from("123")
        val expectedVehicle = VehicleID.from("123")
        val expectedService = Service.create(
            "description", ServiceCategoryID.from("123"), BigDecimal("10.00")
        )
        val expectedServiceOrderItem = ServiceOrderItem.create(
            ServiceOrderID.from("123"), expectedService, aQuantity = 2
        )
        val expectedItemsCount = 1

        val aServiceOrder = ServiceOrder.create(
            expectedCustomer, expectedVehicle
        )
        val actualUpdatedAt = aServiceOrder.updatedAt
        val actualTotal = aServiceOrder.total

        Thread.sleep(1) // To ensure the updatedAt is different
        aServiceOrder.addItem(expectedServiceOrderItem)
        aServiceOrder.complete()

        assertAll(
            "Service Order validation",
            { assertNotNull(aServiceOrder.getId()) },
            { assertEquals(expectedCustomer, aServiceOrder.customer) },
            { assertEquals(expectedVehicle, aServiceOrder.vehicle) },
            { assertEquals(ServiceOrderStatus.COMPLETED, aServiceOrder.status) },
            { assertEquals(expectedItemsCount, aServiceOrder.items.size) },
            { assertEquals(BigDecimal("20.00"), aServiceOrder.total) },
            { assertTrue(actualTotal < aServiceOrder.total) },
            { assertNull(aServiceOrder.checkList) },
            { assertNotNull(aServiceOrder.createdAt) },
            { assertNotNull(aServiceOrder.updatedAt) },
            { assertNotNull(aServiceOrder.completedAt) },
            { assertTrue(actualUpdatedAt.isBefore(aServiceOrder.updatedAt)) },
            { assertEquals(aServiceOrder.completedAt, aServiceOrder.updatedAt) })
    }

}