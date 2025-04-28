package com.furious.vehicle.inspect.domain.service

import com.furious.vehicle.inspect.domain.exceptions.NotificationException
import com.furious.vehicle.inspect.domain.servicecategory.ServiceCategory
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ServiceTest {

    @Test
    fun `given a valid params when call create then instantiate a Service`() {
        val expectedDescription = "Performance remap"
        val expectedPrice = "100.00"
        val expectedCategory = ServiceCategory.create("Performance", "Performance remap")

        val aService = Service.create(expectedDescription, expectedCategory.getId(), expectedPrice.toBigDecimal())

        assertAll(
            "Service validation",
            { assertNotNull(aService.getId()) },
            { assertEquals(expectedDescription, aService.description) },
            { assertEquals(expectedPrice.toBigDecimal(), aService.defaultPrice) },
            { assertNotNull(aService.createdAt) },
            { assertNotNull(aService.updatedAt) })
    }

    @Test
    fun `given params with a description length less then 3 characters when call create then should throw NotificationException`() {
        val expectedDescription = "Pe"
        val expectedErrorMessage = "Failed to create an Aggregate Service"
        val expectedConstraintErrorMessage = "'description' must be between 3 and 255 characters"
        val expectedErrorCount = 1

        val expectedCategory = ServiceCategory.create("Performance", "Performance remap")

        val actualException = assertThrows<NotificationException> {
            Service.create(expectedDescription, expectedCategory.getId())
        }

        assertAll(
            "Service exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedConstraintErrorMessage, actualException.getErrors()[0].message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) })
    }

    @Test
    fun `given params with a description length greater then 255 characters when call create then should throw NotificationException`() {
        val expectedDescription =
            "This backend project was meticulously engineered with a strong focus on scalability, modular design, performance optimization, and maintainability. It ensures secure communication, robust data handling, and seamless integration across distributed systems..."
        val expectedErrorMessage = "Failed to create an Aggregate Service"
        val expectedConstraintErrorMessage = "'description' must be between 3 and 255 characters"
        val expectedErrorCount = 1

        val expectedCategory = ServiceCategory.create("Performance", "Performance remap")

        val actualException = assertThrows<NotificationException> {
            Service.create(expectedDescription, expectedCategory.getId())
        }

        assertAll(
            "Service exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedConstraintErrorMessage, actualException.getErrors()[0].message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) })
    }

    @Test
    fun `given params with a blank description when call create then should throw NotificationException`() {
        val expectedDescription = "  "
        val expectedErrorMessage = "Failed to create an Aggregate Service"
        val expectedConstraintErrorMessage = "'description' cannot be blank"
        val expectedErrorCount = 1

        val expectedCategory = ServiceCategory.create("Performance", "Performance remap")

        val actualException = assertThrows<NotificationException> {
            Service.create(expectedDescription, expectedCategory.getId())
        }

        assertAll(
            "Service exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedConstraintErrorMessage, actualException.getErrors()[0].message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) })
    }

    @Test
    fun `given params with an empty description when call create then should throw NotificationException`() {
        val expectedDescription = ""
        val expectedErrorMessage = "Failed to create an Aggregate Service"
        val expectedConstraintErrorMessage = "'description' cannot be empty"
        val expectedErrorCount = 1

        val expectedCategory = ServiceCategory.create("Performance", "Performance remap")

        val actualException = assertThrows<NotificationException> {
            Service.create(expectedDescription, expectedCategory.getId())
        }

        assertAll(
            "Service exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedConstraintErrorMessage, actualException.getErrors()[0].message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) })
    }

    @Test
    fun `given params with a negative price when call create then should throw NotificationException`() {
        val expectedDescription = "Performance remap"
        val expectedErrorMessage = "Failed to create an Aggregate Service"
        val expectedPrice = "-100.00"
        val expectedPriceErrorMessage = "'price' cannot be negative"
        val expectedErrorCount = 1

        val expectedCategory = ServiceCategory.create("Performance", "Performance remap")

        val actualException = assertThrows<NotificationException> {
            Service.create(expectedDescription, expectedCategory.getId(), expectedPrice.toBigDecimal())
        }

        assertAll(
            "Service exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedPriceErrorMessage, actualException.getErrors()[0].message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) })
    }

    @Test
    fun `given params with a negative price and an empty description when call create then should throw NotificationException with 2 errors`() {
        val expectedDescription = ""
        val expectedErrorMessage = "Failed to create an Aggregate Service"
        val expectedPrice = "-100.00"
        val expectedPriceErrorMessage = "'price' cannot be negative"
        val expectedConstraintErrorMessage = "'description' cannot be empty"
        val expectedErrorCount = 2

        val expectedCategory = ServiceCategory.create("Performance", "Performance remap")

        val actualException = assertThrows<NotificationException> {
            Service.create(expectedDescription, expectedCategory.getId(), expectedPrice.toBigDecimal())
        }

        assertAll(
            "Service exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedConstraintErrorMessage, actualException.getErrors()[0].message) },
            { assertEquals(expectedPriceErrorMessage, actualException.getErrors()[1].message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) })
    }

    @Test
    fun `given a Service when call update then should return Service updated`() {
        val expectedDescription = "Performance remap"
        val expectedPrice = "100.00"
        val expectedCategory = ServiceCategory.create("Performance", "Performance remap")

        val aService = Service.create(expectedDescription, expectedCategory.getId(), expectedPrice.toBigDecimal())

        val updatedDescription = "Performance remap updated"
        val updatedPrice = "200.00"
        val previousUpdatedAt = aService.updatedAt

        Thread.sleep(1) // Ensure updatedAt is different
        aService.update(expectedCategory.getId(), updatedDescription, updatedPrice.toBigDecimal())

        assertAll(
            "Service validation",
            { assertEquals(updatedDescription, aService.description) },
            { assertEquals(updatedPrice.toBigDecimal(), aService.defaultPrice) },
            { assertEquals(previousUpdatedAt, aService.createdAt) },
            { assertTrue(aService.updatedAt.isAfter(previousUpdatedAt)) })
    }

    @Test
    fun `given a Service when call update with a different ServiceCategory then should be Ok`() {
        val expectedDescription = "Performance remap"
        val expectedPrice = "100.00"
        val expectedCategory = ServiceCategory.create("Performance", "Performance remap")

        val aService = Service.create(expectedDescription, expectedCategory.getId(), expectedPrice.toBigDecimal())

        val updatedCategory = ServiceCategory.create("Upgrade")
        val updatedDescription = "Performance remap updated"
        val updatedPrice = "200.00"
        val previousUpdatedAt = aService.updatedAt

        Thread.sleep(1) // Ensure updatedAt is different
        aService.update(updatedCategory.getId(), updatedDescription, updatedPrice.toBigDecimal())

        assertAll(
            "Service validation",
            { assertEquals(updatedDescription, aService.description) },
            { assertEquals(updatedCategory.getId().getValue(), aService.category.getValue()) },
            { assertEquals(updatedPrice.toBigDecimal(), aService.defaultPrice) },
            { assertEquals(previousUpdatedAt, aService.createdAt) },
            { assertTrue(aService.updatedAt.isAfter(previousUpdatedAt)) })
    }

    @Test
    fun `given a Service when call update with an empty description then should throw NotificationException`() {
        val expectedCategory = ServiceCategory.create("Performance", "Performance remap")
        val expectedErrorMessage = "Failed to update an Aggregate Service"
        val expectedConstraintErrorMessage = "'description' cannot be empty"
        val expectedErrorCount = 1

        val aService = Service.create("Performance remap", expectedCategory.getId(), "100.00".toBigDecimal())

        val updatedDescription = ""
        val updatedPrice = "200.00"

        val actualException = assertThrows<NotificationException> {
            aService.update(expectedCategory.getId(), updatedDescription, updatedPrice.toBigDecimal())
        }

        assertAll(
            "Service exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedConstraintErrorMessage, actualException.getErrors()[0].message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) })
    }

    @Test
    fun `given a Service when call update with a blank description then should throw NotificationException`() {
        val expectedCategory = ServiceCategory.create("Performance", "Performance remap")
        val expectedErrorMessage = "Failed to update an Aggregate Service"
        val expectedConstraintErrorMessage = "'description' cannot be blank"
        val expectedErrorCount = 1

        val aService = Service.create("Performance remap", expectedCategory.getId(), "100.00".toBigDecimal())

        val updatedDescription = "  "
        val updatedPrice = "200.00"

        val actualException = assertThrows<NotificationException> {
            aService.update(expectedCategory.getId(), updatedDescription, updatedPrice.toBigDecimal())
        }

        assertAll(
            "Service exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedConstraintErrorMessage, actualException.getErrors()[0].message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) })
    }

    @Test
    fun `given a Service when call update with a description length less then 3 chars then should throw NotificationException`() {
        val expectedCategory = ServiceCategory.create("Performance", "Performance remap")
        val expectedErrorMessage = "Failed to update an Aggregate Service"
        val expectedConstraintErrorMessage = "'description' must be between 3 and 255 characters"
        val expectedErrorCount = 1

        val aService = Service.create("Performance remap", expectedCategory.getId(), "100.00".toBigDecimal())

        val updatedDescription = "Pe"
        val updatedPrice = "200.00"

        val actualException = assertThrows<NotificationException> {
            aService.update(expectedCategory.getId(), updatedDescription, updatedPrice.toBigDecimal())
        }

        assertAll(
            "Service exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedConstraintErrorMessage, actualException.getErrors()[0].message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) })
    }

    @Test
    fun `given a Service when call update with a description length greater then 3 chars then should throw NotificationException`() {
        val expectedCategory = ServiceCategory.create("Performance", "Performance remap")
        val expectedErrorMessage = "Failed to update an Aggregate Service"
        val expectedConstraintErrorMessage = "'description' must be between 3 and 255 characters"
        val expectedErrorCount = 1

        val aService = Service.create("Performance remap", expectedCategory.getId(), "100.00".toBigDecimal())

        val updatedDescription =
            "This backend project was meticulously engineered with a strong focus on scalability, modular design, performance optimization, and maintainability. It ensures secure communication, robust data handling, and seamless integration across distributed systems..."
        val updatedPrice = "200.00"

        val actualException = assertThrows<NotificationException> {
            aService.update(expectedCategory.getId(), updatedDescription, updatedPrice.toBigDecimal())
        }

        assertAll(
            "Service exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedConstraintErrorMessage, actualException.getErrors()[0].message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) })
    }

    @Test
    fun `given a Service when call update with a negative price then should throw NotificationException`() {
        val expectedCategory = ServiceCategory.create("Performance", "Performance remap")
        val expectedErrorMessage = "Failed to update an Aggregate Service"
        val expectedConstraintErrorMessage = "'price' cannot be negative"
        val expectedErrorCount = 1

        val aService = Service.create("Performance remap", expectedCategory.getId(), "100.00".toBigDecimal())

        val updatedDescription = "Performance remap updated"
        val updatedPrice = "-200.00"

        val actualException = assertThrows<NotificationException> {
            aService.update(expectedCategory.getId(), updatedDescription, updatedPrice.toBigDecimal())
        }

        assertAll(
            "Service exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedConstraintErrorMessage, actualException.getErrors()[0].message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) })
    }

}