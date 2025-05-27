package com.furious.vehicle.inspect.domain.customer.vehicle

import com.furious.vehicle.inspect.domain.customer.CustomerID
import com.furious.vehicle.inspect.domain.exceptions.NotificationException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.Year

class VehicleTest {

    @Test
    fun `given a valid params when call create then instantiate a Vehicle`() {
        val expectedCustomerID = CustomerID.from("123")
        val expectedMake = "Honda"
        val expectedModel = "Civic SI"
        val expectedYear = 2014
        val expectedColor = "Orange"
        val expectedLicensePlate = LicensePlate.create("ABC-1234")
        val expectedMileage = Mileage.create(UnitOfMeasurement.KM, 50000.00)

        val aVehicle = Vehicle.create(
            expectedCustomerID,
            expectedMake,
            expectedModel,
            expectedYear,
            expectedColor,
            expectedLicensePlate,
            expectedMileage
        )

        assertAll(
            "Vehicle validation",
            { assertEquals(expectedCustomerID, aVehicle.customerID) },
            { assertEquals(expectedMake, aVehicle.make) },
            { assertEquals(expectedModel, aVehicle.model) },
            { assertEquals(expectedYear, aVehicle.year) },
            { assertEquals(expectedColor, aVehicle.color) },
            { assertEquals(expectedLicensePlate, aVehicle.licensePlate) },
            { assertEquals(expectedMileage, aVehicle.mileage) },
            { assertNotNull(aVehicle.createdAt) },
            { assertNotNull(aVehicle.updatedAt) })
    }

    @Test
    fun `given a valid params without mileage when call create then should instantiate a Vehicle`() {
        val expectedCustomerID = CustomerID.from("123")
        val expectedMake = "Honda"
        val expectedModel = "Civic SI"
        val expectedYear = 2014
        val expectedColor = "Orange"
        val expectedLicensePlate = LicensePlate.create("ABC-1234")
        val expectedMileage = null

        val aVehicle = Vehicle.create(
            expectedCustomerID, expectedMake, expectedModel, expectedYear, expectedColor, expectedLicensePlate
        )

        assertAll(
            "Vehicle validation",
            { assertEquals(expectedCustomerID, aVehicle.customerID) },
            { assertEquals(expectedMake, aVehicle.make) },
            { assertEquals(expectedModel, aVehicle.model) },
            { assertEquals(expectedYear, aVehicle.year) },
            { assertEquals(expectedColor, aVehicle.color) },
            { assertEquals(expectedLicensePlate, aVehicle.licensePlate) },
            { assertEquals(expectedMileage, aVehicle.mileage) },
            { assertNotNull(aVehicle.createdAt) },
            { assertNotNull(aVehicle.updatedAt) })
    }

    @Test
    fun `given an invalid make with less than 2 chars when call create then should throw a Notification exception`() {
        val expectedCustomerID = CustomerID.from("123")
        val expectedMake = "a"
        val expectedModel = "Civic SI"
        val expectedYear = 2014
        val expectedColor = "Orange"
        val expectedLicensePlate = LicensePlate.create("ABC-1234")
        val expectedMileage = Mileage.create(UnitOfMeasurement.KM, 50000.00)
        val expectedErrorMessage = "Failed to create an Entity Vehicle"
        val expectedMakeErrorMessage = "'make' must be between 2 and 20 characters"
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            Vehicle.create(
                expectedCustomerID,
                expectedMake,
                expectedModel,
                expectedYear,
                expectedColor,
                expectedLicensePlate,
                expectedMileage
            )
        }

        assertAll(
            "Vehicle exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedMakeErrorMessage, actualException.getErrors().first().message) },
        )
    }

    @Test
    fun `given an invalid make with more than 20 chars when call create then should throw a Notification exception`() {
        val expectedCustomerID = CustomerID.from("123")
        val expectedMake = "aaaaa aaaa aaaaa aaaa"
        val expectedModel = "Civic SI"
        val expectedYear = 2014
        val expectedColor = "Orange"
        val expectedLicensePlate = LicensePlate.create("ABC-1234")
        val expectedMileage = Mileage.create(UnitOfMeasurement.KM, 50000.00)
        val expectedErrorMessage = "Failed to create an Entity Vehicle"
        val expectedMakeErrorMessage = "'make' must be between 2 and 20 characters"
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            Vehicle.create(
                expectedCustomerID,
                expectedMake,
                expectedModel,
                expectedYear,
                expectedColor,
                expectedLicensePlate,
                expectedMileage
            )
        }

        assertAll(
            "Vehicle exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedMakeErrorMessage, actualException.getErrors().first().message) },
        )
    }

    @Test
    fun `given an invalid model with less than 2 chars when call create then should throw a Notification exception`() {
        val expectedCustomerID = CustomerID.from("123")
        val expectedMake = "Honda"
        val expectedModel = "C"
        val expectedYear = 2014
        val expectedColor = "Orange"
        val expectedLicensePlate = LicensePlate.create("ABC-1234")
        val expectedMileage = Mileage.create(UnitOfMeasurement.KM, 50000.00)
        val expectedErrorMessage = "Failed to create an Entity Vehicle"
        val expectedModelErrorMessage = "'model' must be between 2 and 30 characters"
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            Vehicle.create(
                expectedCustomerID,
                expectedMake,
                expectedModel,
                expectedYear,
                expectedColor,
                expectedLicensePlate,
                expectedMileage
            )
        }

        assertAll(
            "Vehicle exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedModelErrorMessage, actualException.getErrors().first().message) },
        )
    }

    @Test
    fun `given an invalid color with less than 3 chars when call create then should throw a Notification exception`() {
        val expectedCustomerID = CustomerID.from("123")
        val expectedMake = "Honda"
        val expectedModel = "Civic SI"
        val expectedYear = 2014
        val expectedColor = "Or"
        val expectedLicensePlate = LicensePlate.create("ABC-1234")
        val expectedMileage = Mileage.create(UnitOfMeasurement.KM, 50000.00)
        val expectedErrorMessage = "Failed to create an Entity Vehicle"
        val expectedModelErrorMessage = "'color' must be between 3 and 30 characters"
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            Vehicle.create(
                expectedCustomerID,
                expectedMake,
                expectedModel,
                expectedYear,
                expectedColor,
                expectedLicensePlate,
                expectedMileage
            )
        }

        assertAll(
            "Vehicle exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedModelErrorMessage, actualException.getErrors().first().message) },
        )
    }

    @Test
    fun `given an invalid model with more than 30 chars when call create then should throw a Notification exception`() {
        val expectedCustomerID = CustomerID.from("123")
        val expectedMake = "Honda"
        val expectedModel = "aaaaa aaaa aaaaa aaaa aaaa aaaa"
        val expectedYear = 2014
        val expectedColor = "Orange"
        val expectedLicensePlate = LicensePlate.create("ABC-1234")
        val expectedMileage = Mileage.create(UnitOfMeasurement.KM, 50000.00)
        val expectedErrorMessage = "Failed to create an Entity Vehicle"
        val expectedModelErrorMessage = "'model' must be between 2 and 30 characters"
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            Vehicle.create(
                expectedCustomerID,
                expectedMake,
                expectedModel,
                expectedYear,
                expectedColor,
                expectedLicensePlate,
                expectedMileage
            )
        }

        assertAll(
            "Vehicle exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedModelErrorMessage, actualException.getErrors().first().message) },
        )
    }

    @Test
    fun `given an invalid color with more than 30 chars when call create then should throw a Notification exception`() {
        val expectedCustomerID = CustomerID.from("123")
        val expectedMake = "Honda"
        val expectedModel = "Civic SI"
        val expectedYear = 2014
        val expectedColor = "aaaaa aaaa aaaaa aaaa aaaa aaaa"
        val expectedLicensePlate = LicensePlate.create("ABC-1234")
        val expectedMileage = Mileage.create(UnitOfMeasurement.KM, 50000.00)
        val expectedErrorMessage = "Failed to create an Entity Vehicle"
        val expectedModelErrorMessage = "'color' must be between 3 and 30 characters"
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            Vehicle.create(
                expectedCustomerID,
                expectedMake,
                expectedModel,
                expectedYear,
                expectedColor,
                expectedLicensePlate,
                expectedMileage
            )
        }

        assertAll(
            "Vehicle exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedModelErrorMessage, actualException.getErrors().first().message) },
        )
    }

    @Test
    fun `given an invalid empty make when call create then should throw a Notification exception`() {
        val expectedCustomerID = CustomerID.from("123")
        val expectedMake = ""
        val expectedModel = "Civic SI"
        val expectedYear = 2014
        val expectedColor = "Orange"
        val expectedLicensePlate = LicensePlate.create("ABC-1234")
        val expectedMileage = Mileage.create(UnitOfMeasurement.KM, 50000.00)
        val expectedErrorMessage = "Failed to create an Entity Vehicle"
        val expectedMakeErrorMessage = "'make' cannot be empty"
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            Vehicle.create(
                expectedCustomerID,
                expectedMake,
                expectedModel,
                expectedYear,
                expectedColor,
                expectedLicensePlate,
                expectedMileage
            )
        }

        assertAll(
            "Vehicle exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedMakeErrorMessage, actualException.getErrors().first().message) },
        )
    }

    @Test
    fun `given an invalid blank model when call create then should throw a Notification exception`() {
        val expectedCustomerID = CustomerID.from("123")
        val expectedMake = "Honda"
        val expectedModel = "  "
        val expectedYear = 2014
        val expectedColor = "Orange"
        val expectedLicensePlate = LicensePlate.create("ABC-1234")
        val expectedMileage = Mileage.create(UnitOfMeasurement.KM, 50000.00)
        val expectedErrorMessage = "Failed to create an Entity Vehicle"
        val expectedModelErrorMessage = "'model' cannot be blank"
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            Vehicle.create(
                expectedCustomerID,
                expectedMake,
                expectedModel,
                expectedYear,
                expectedColor,
                expectedLicensePlate,
                expectedMileage
            )
        }

        assertAll(
            "Vehicle exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedModelErrorMessage, actualException.getErrors().first().message) },
        )
    }

    @Test
    fun `given an invalid blank color when call create then should throw a Notification exception`() {
        val expectedCustomerID = CustomerID.from("123")
        val expectedMake = "Honda"
        val expectedModel = "Civic SI"
        val expectedYear = 2014
        val expectedColor = "   "
        val expectedLicensePlate = LicensePlate.create("ABC-1234")
        val expectedMileage = Mileage.create(UnitOfMeasurement.KM, 50000.00)
        val expectedErrorMessage = "Failed to create an Entity Vehicle"
        val expectedModelErrorMessage = "'color' cannot be blank"
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            Vehicle.create(
                expectedCustomerID,
                expectedMake,
                expectedModel,
                expectedYear,
                expectedColor,
                expectedLicensePlate,
                expectedMileage
            )
        }

        assertAll(
            "Vehicle exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedModelErrorMessage, actualException.getErrors().first().message) },
        )
    }

    @Test
    fun `given an invalid empty model when call create then should throw a Notification exception`() {
        val expectedCustomerID = CustomerID.from("123")
        val expectedMake = "Honda"
        val expectedModel = ""
        val expectedYear = 2014
        val expectedColor = "Orange"
        val expectedLicensePlate = LicensePlate.create("ABC-1234")
        val expectedMileage = Mileage.create(UnitOfMeasurement.KM, 50000.00)
        val expectedErrorMessage = "Failed to create an Entity Vehicle"
        val expectedModelErrorMessage = "'model' cannot be empty"
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            Vehicle.create(
                expectedCustomerID,
                expectedMake,
                expectedModel,
                expectedYear,
                expectedColor,
                expectedLicensePlate,
                expectedMileage
            )
        }

        assertAll(
            "Vehicle exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedModelErrorMessage, actualException.getErrors().first().message) },
        )
    }

    @Test
    fun `given an invalid empty color when call create then should throw a Notification exception`() {
        val expectedCustomerID = CustomerID.from("123")
        val expectedMake = "Honda"
        val expectedModel = "Civic SI"
        val expectedYear = 2014
        val expectedColor = ""
        val expectedLicensePlate = LicensePlate.create("ABC-1234")
        val expectedMileage = Mileage.create(UnitOfMeasurement.KM, 50000.00)
        val expectedErrorMessage = "Failed to create an Entity Vehicle"
        val expectedModelErrorMessage = "'color' cannot be empty"
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            Vehicle.create(
                expectedCustomerID,
                expectedMake,
                expectedModel,
                expectedYear,
                expectedColor,
                expectedLicensePlate,
                expectedMileage
            )
        }

        assertAll(
            "Vehicle exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedModelErrorMessage, actualException.getErrors().first().message) },
        )
    }

    @Test
    fun `given an invalid license plate format when call create then should throw a Notification exception`() {
        val expectedCustomerID = CustomerID.from("123")
        val expectedMake = "Honda"
        val expectedModel = "Civic SI"
        val expectedYear = 2014
        val expectedColor = "Orange"
        val expectedLicensePlate = LicensePlate.create("ABC @234")
        val expectedMileage = Mileage.create(UnitOfMeasurement.KM, 50000.00)
        val expectedErrorMessage = "Failed to create an Entity Vehicle"
        val expectedLicensePlateErrorMessage = "invalid 'license plate' format"
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            Vehicle.create(
                expectedCustomerID,
                expectedMake,
                expectedModel,
                expectedYear,
                expectedColor,
                expectedLicensePlate,
                expectedMileage
            )
        }

        assertAll(
            "Vehicle exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedLicensePlateErrorMessage, actualException.getErrors().first().message) },
        )
    }

    @Test
    fun `given params with an invalid mileage when call create then should throw Notification exception`() {
        val expectedCustomerID = CustomerID.from("123")
        val expectedMake = "Honda"
        val expectedModel = "Civic SI"
        val expectedYear = 2014
        val expectedColor = "Orange"
        val expectedLicensePlate = LicensePlate.create("ABC-1234")
        val expectedMileage = Mileage.create(UnitOfMeasurement.KM, -20.00)
        val expectedErrorMessage = "Failed to create an Entity Vehicle"
        val expectedMileageErrorMessage = "'mileage' cannot be negative"
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            Vehicle.create(
                expectedCustomerID,
                expectedMake,
                expectedModel,
                expectedYear,
                expectedColor,
                expectedLicensePlate,
                expectedMileage
            )
        }

        assertAll(
            "Vehicle exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedMileageErrorMessage, actualException.getErrors().first().message) },
        )
    }

    @Test
    fun `given params with a negative year when call create then should throw Notification exception`() {
        val expectedCustomerID = CustomerID.from("123")
        val expectedMake = "Honda"
        val expectedModel = "Civic SI"
        val expectedYear = -2014
        val expectedColor = "Orange"
        val expectedLicensePlate = LicensePlate.create("ABC-1234")
        val expectedMileage = Mileage.create(UnitOfMeasurement.KM, 20.00)
        val expectedErrorMessage = "Failed to create an Entity Vehicle"
        val expectedYearErrorMessage = "'year' cannot be negative"
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            Vehicle.create(
                expectedCustomerID,
                expectedMake,
                expectedModel,
                expectedYear,
                expectedColor,
                expectedLicensePlate,
                expectedMileage
            )
        }

        assertAll(
            "Vehicle exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedYearErrorMessage, actualException.getErrors().first().message) },
        )
    }

    @Test
    fun `given params with a year more than 2 years ahead when call create then should throw Notification exception`() {
        val expectedCustomerID = CustomerID.from("123")
        val expectedMake = "Honda"
        val expectedModel = "Civic SI"
        val expectedColor = "Orange"
        val expectedYear = Year.now().value + 2
        val expectedLicensePlate = LicensePlate.create("ABC-1234")
        val expectedMileage = Mileage.create(UnitOfMeasurement.KM, 20.00)
        val expectedErrorMessage = "Failed to create an Entity Vehicle"
        val expectedYearErrorMessage = "'year' cannot be more than 2 years ahead"
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            Vehicle.create(
                expectedCustomerID,
                expectedMake,
                expectedModel,
                expectedYear,
                expectedColor,
                expectedLicensePlate,
                expectedMileage
            )
        }

        assertAll(
            "Vehicle exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedYearErrorMessage, actualException.getErrors().first().message) },
        )
    }

    @Test
    fun `given a valid params when call update then return a Customer updated`() {
        val expectedCustomerID = CustomerID.from("123")
        val expectedMake = "Honda"
        val expectedModel = "Civic SI"
        val expectedYear = 2014
        val expectedColor = "Orange"
        val expectedLicensePlate = LicensePlate.create("ABC-1234")
        val expectedMileage = Mileage.create(UnitOfMeasurement.KM, 50000.00)

        val aVehicle = Vehicle.create(
            expectedCustomerID, "Subaru", "WRX", 2016, expectedColor, expectedLicensePlate, expectedMileage
        )

        val aCreatedAt = aVehicle.createdAt
        val anUpdatedAt = aVehicle.updatedAt

        Thread.sleep(1)
        val actualVehicle = aVehicle.update(
            expectedCustomerID,
            expectedMake,
            expectedModel,
            expectedYear,
            expectedColor,
            expectedLicensePlate,
            expectedMileage
        )

        assertAll(
            "Vehicle validation",
            { assertEquals(expectedCustomerID, actualVehicle.customerID) },
            { assertEquals(expectedMake, actualVehicle.make) },
            { assertEquals(expectedModel, actualVehicle.model) },
            { assertEquals(expectedYear, actualVehicle.year) },
            { assertEquals(expectedColor, actualVehicle.color) },
            { assertEquals(expectedLicensePlate, actualVehicle.licensePlate) },
            { assertEquals(expectedMileage, actualVehicle.mileage) },
            { assertEquals(aCreatedAt, actualVehicle.createdAt) },
            { assertTrue(anUpdatedAt.isBefore(actualVehicle.updatedAt)) })
    }

    @Test
    fun `given a valid params when call update with a mileage then return a Customer updated`() {
        val expectedCustomerID = CustomerID.from("123")
        val expectedMake = "Honda"
        val expectedModel = "Civic SI"
        val expectedYear = 2014
        val expectedColor = "Orange"
        val expectedLicensePlate = LicensePlate.create("ABC-1234")
        val expectedMileage = Mileage.create(UnitOfMeasurement.KM, 50000.00)

        val aVehicle = Vehicle.create(
            expectedCustomerID, "Subaru", "WRX", 2016, expectedColor, expectedLicensePlate
        )

        val aCreatedAt = aVehicle.createdAt
        val anUpdatedAt = aVehicle.updatedAt

        Thread.sleep(1)
        val actualVehicle = aVehicle.update(
            expectedCustomerID,
            expectedMake,
            expectedModel,
            expectedYear,
            expectedColor,
            expectedLicensePlate,
            expectedMileage
        )

        assertAll(
            "Vehicle validation",
            { assertEquals(expectedCustomerID, actualVehicle.customerID) },
            { assertEquals(expectedMake, actualVehicle.make) },
            { assertEquals(expectedModel, actualVehicle.model) },
            { assertEquals(expectedYear, actualVehicle.year) },
            { assertEquals(expectedColor, actualVehicle.color) },
            { assertEquals(expectedLicensePlate, actualVehicle.licensePlate) },
            { assertEquals(expectedMileage, actualVehicle.mileage) },
            { assertEquals(aCreatedAt, actualVehicle.createdAt) },
            { assertTrue(anUpdatedAt.isBefore(actualVehicle.updatedAt)) })
    }

    @Test
    fun `given a vehicle when call update with invalid year and model then should throw Notification exception`() {
        val expectedCustomerID = CustomerID.from("123")
        val expectedMake = "Subaru"
        val expectedModel = " "
        val expectedYear = Year.now().value + 2
        val expectedColor = "Orange"
        val expectedLicensePlate = LicensePlate.create("ABC-1234")
        val expectedMileage = Mileage.create(UnitOfMeasurement.KM, 20.00)
        val expectedErrorMessage = "Failed to update an Entity Vehicle"
        val expectedModelErrorMessage = "'model' cannot be blank"
        val expectedYearErrorMessage = "'year' cannot be more than 2 years ahead"
        val expectedErrorCount = 2

        val aVehicle = Vehicle.create(
            expectedCustomerID, "Subaru", "WRX", 2016, expectedColor, expectedLicensePlate
        )

        val actualException = assertThrows<NotificationException> {
            aVehicle.update(
                expectedCustomerID,
                expectedMake,
                expectedModel,
                expectedYear,
                expectedColor,
                expectedLicensePlate,
                expectedMileage
            )
        }

        assertAll(
            "Vehicle exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedModelErrorMessage, actualException.getErrors()[0].message) },
            { assertEquals(expectedYearErrorMessage, actualException.getErrors()[1].message) },
        )
    }

}