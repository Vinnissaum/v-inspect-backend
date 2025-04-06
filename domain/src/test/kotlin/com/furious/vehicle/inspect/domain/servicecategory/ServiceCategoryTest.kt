package com.furious.vehicle.inspect.domain.servicecategory

import com.furious.vehicle.inspect.domain.exceptions.NotificationException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ServiceCategoryTest {

    @Test
    fun `given a valid params when call create then instantiate a ServiceCategory`() {
        val expectedName = "Performance"
        val expectedDescription = "Performance remap"

        val aServiceCategory = ServiceCategory.create(expectedName, expectedDescription)

        assertAll(
            "Service Category validation",
            { assertNotNull(aServiceCategory.getId()) },
            { assertEquals(expectedName, aServiceCategory.name) },
            { assertEquals(expectedDescription, aServiceCategory.description) },
            { assertNotNull(aServiceCategory.createdAt) },
            { assertNotNull(aServiceCategory.updatedAt) })
    }

    @Test
    fun `given a valid params with a null description when call create then should be Ok`() {
        val expectedName = "Performance"
        val expectedDescription = null

        val aServiceCategory = ServiceCategory.create(expectedName, expectedDescription)

        assertAll(
            "Service Category validation",
            { assertNotNull(aServiceCategory.getId()) },
            { assertEquals(expectedName, aServiceCategory.name) },
            { assertEquals(expectedDescription, aServiceCategory.description) },
            { assertNotNull(aServiceCategory.createdAt) },
            { assertNotNull(aServiceCategory.updatedAt) })
    }

    @Test
    fun `given a ServiceCategory with a name length less than 3 chars when call create then should throw NotificationException`() {
        val expectedName = "Pe"
        val expectedDescription = "Performance remap"
        val expectedErrorMessage = "Failed to create an Aggregate ServiceCategory"
        val expectedConstraintErrorMessage = "'name' must be between 3 and 255 characters"
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            ServiceCategory.create(expectedName, expectedDescription)
        }

        assertAll(
            "Service Category exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedConstraintErrorMessage, actualException.getErrors()[0].message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) })
    }

    @Test
    fun `given a ServiceCategory with a name length greater than 255 chars when call create then should throw NotificationException`() {
        val expectedName =
            "This backend project was meticulously engineered with a strong focus on scalability, modular design, performance optimization, and maintainability. It ensures secure communication, robust data handling, and seamless integration across distributed systems..."
        val expectedDescription = "Performance remap"
        val expectedErrorMessage = "Failed to create an Aggregate ServiceCategory"
        val expectedConstraintErrorMessage = "'name' must be between 3 and 255 characters"
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            ServiceCategory.create(expectedName, expectedDescription)
        }

        assertAll(
            "Service Category exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedConstraintErrorMessage, actualException.getErrors()[0].message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) })
    }

    @Test
    fun `given a ServiceCategory when call update then should return ServiceCategory updated`() {
        val expectedName = "Performance"
        val expectedDescription = "Performance remap"

        var aServiceCategory = ServiceCategory.create("Brakes", null)
        val expectedId = aServiceCategory.getId()
        val actualUpdatedAt = aServiceCategory.updatedAt

        Thread.sleep(1) // To ensure the updatedAt is different
        aServiceCategory = aServiceCategory.update(expectedName, expectedDescription)

        assertAll(
            "Service Category validation",
            { assertEquals(expectedId, aServiceCategory.getId()) },
            { assertEquals(expectedName, aServiceCategory.name) },
            { assertEquals(expectedDescription, aServiceCategory.description) },
            { assertNotNull(aServiceCategory.createdAt) },
            { assertTrue(aServiceCategory.updatedAt.isAfter(actualUpdatedAt)) })
    }

    @Test
    fun `given a ServiceCategory when call update with a null description then should be Ok`() {
        val expectedName = "Performance"
        val expectedDescription = null

        var aServiceCategory = ServiceCategory.create("Brakes", "Brakes maintenance")
        val expectedId = aServiceCategory.getId()
        val actualUpdatedAt = aServiceCategory.updatedAt

        Thread.sleep(1) // To ensure the updatedAt is different
        aServiceCategory = aServiceCategory.update(expectedName, expectedDescription)

        assertAll(
            "Service Category validation",
            { assertEquals(expectedId, aServiceCategory.getId()) },
            { assertEquals(expectedName, aServiceCategory.name) },
            { assertEquals(expectedDescription, aServiceCategory.description) },
            { assertNotNull(aServiceCategory.createdAt) },
            { assertTrue(aServiceCategory.updatedAt.isAfter(actualUpdatedAt)) })
    }


    @Test
    fun `given a valid ServiceCategory with a name length less than 3 chars when call create then should throw NotificationException`() {
        val expectedName = "Pe"
        val expectedDescription = "Performance remap"
        val expectedErrorMessage = "Failed to update an Aggregate ServiceCategory"
        val expectedConstraintErrorMessage = "'name' must be between 3 and 255 characters"
        val aServiceCategory = ServiceCategory.create("Brakes", null)
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            aServiceCategory.update(expectedName, expectedDescription)
        }

        assertAll(
            "Service Category exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedConstraintErrorMessage, actualException.getErrors()[0].message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) })
    }

    @Test
    fun `given a valid ServiceCategory when call update with a name length greater than 255 chars when call create then should throw NotificationException`() {
        val expectedName =
            "This backend project was meticulously engineered with a strong focus on scalability, modular design, performance optimization, and maintainability. It ensures secure communication, robust data handling, and seamless integration across distributed systems..."
        val expectedDescription = "Performance remap"
        val expectedErrorMessage = "Failed to update an Aggregate ServiceCategory"
        val expectedConstraintErrorMessage = "'name' must be between 3 and 255 characters"
        val expectedErrorCount = 1
        val aServiceCategory = ServiceCategory.create("Brakes", null)

        val actualException = assertThrows<NotificationException> {
            aServiceCategory.update(expectedName, expectedDescription)
        }

        assertAll(
            "Service Category exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedConstraintErrorMessage, actualException.getErrors()[0].message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) })
    }

}