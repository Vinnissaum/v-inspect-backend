package com.furious.vehicle.inspect.domain.serviceorder

import com.furious.vehicle.inspect.domain.exceptions.NotificationException
import com.furious.vehicle.inspect.domain.serviceorder.valueobject.ChecklistStatus
import com.furious.vehicle.inspect.domain.vehicle.VehicleID
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ChecklistTest {

    @Test
    fun `given a valid params when call create then instantiate a Checklist`() {
        val expectedVehicle = VehicleID.from("123")
        val expectedCustomerSignature = null
        val expectedStatus = ChecklistStatus.PENDING
        val expectedChecklistItems = emptyList<ChecklistItem>()

        val aChecklist = Checklist.create(expectedVehicle)

        assertAll(
            "Checklist validation",
            { assertNotNull(aChecklist.getId()) },
            { assertEquals(expectedVehicle, aChecklist.vehicleID) },
            { assertEquals(expectedCustomerSignature, aChecklist.customerSignature) },
            { assertEquals(expectedStatus, aChecklist.status) },
            { assertEquals(expectedChecklistItems, aChecklist.items) },
            { assertNotNull(aChecklist.createdAt) },
            { assertNotNull(aChecklist.updatedAt) })
    }

    @Test
    fun `given a valid Checklist when call addItem should return Checklist with items`() {
        val expectedVehicle = VehicleID.from("123")
        val expectedCustomerSignature = null
        val expectedStatus = ChecklistStatus.PENDING
        val aChecklist = Checklist.create(expectedVehicle)
        val actualUpdatedAt = aChecklist.updatedAt
        val expectedChecklistItemDescription = "Passenger side mirror broken"
        val expectedChecklistItemPhotoUrl = "photoUrl"
        val expectedItemsCount = 1

        Thread.sleep(1) // To ensure that the updatedAt is different from createdAt
        aChecklist.addItem(expectedChecklistItemDescription, expectedChecklistItemPhotoUrl)

        assertAll(
            "Checklist validation",
            { assertNotNull(aChecklist.getId()) },
            { assertEquals(expectedVehicle, aChecklist.vehicleID) },
            { assertEquals(expectedCustomerSignature, aChecklist.customerSignature) },
            { assertEquals(expectedStatus, aChecklist.status) },
            { assertEquals(expectedItemsCount, aChecklist.items.size) },
            { assertEquals(expectedChecklistItemDescription, aChecklist.items[0].description) },
            { assertEquals(expectedChecklistItemPhotoUrl, aChecklist.items[0].photoUrl) },
            { assertEquals(aChecklist.getId(), aChecklist.items[0].checklistID) },
            { assertTrue(aChecklist.createdAt.isBefore(aChecklist.updatedAt)) },
            { assertTrue(aChecklist.updatedAt.isAfter(actualUpdatedAt)) })
    }

    @Test
    fun `given a valid Checklist when call addItem with a null photoUrl should be Ok`() {
        val expectedVehicle = VehicleID.from("123")
        val expectedCustomerSignature = null
        val expectedStatus = ChecklistStatus.PENDING
        val aChecklist = Checklist.create(expectedVehicle)
        val actualUpdatedAt = aChecklist.updatedAt
        val expectedChecklistItemDescription = "Passenger side mirror broken"
        val expectedChecklistItemPhotoUrl = null
        val expectedItemsCount = 1

        Thread.sleep(1) // To ensure that the updatedAt is different from createdAt
        aChecklist.addItem(expectedChecklistItemDescription, expectedChecklistItemPhotoUrl)

        assertAll(
            "Checklist validation",
            { assertNotNull(aChecklist.getId()) },
            { assertEquals(expectedVehicle, aChecklist.vehicleID) },
            { assertEquals(expectedCustomerSignature, aChecklist.customerSignature) },
            { assertEquals(expectedStatus, aChecklist.status) },
            { assertEquals(expectedItemsCount, aChecklist.items.size) },
            { assertEquals(expectedChecklistItemDescription, aChecklist.items[0].description) },
            { assertEquals(expectedChecklistItemPhotoUrl, aChecklist.items[0].photoUrl) },
            { assertEquals(aChecklist.getId(), aChecklist.items[0].checklistID) },
            { assertTrue(aChecklist.createdAt.isBefore(aChecklist.updatedAt)) },
            { assertTrue(aChecklist.updatedAt.isAfter(actualUpdatedAt)) })
    }

    @Test
    fun `given a valid Checklist when call addItem with checklist description more chars than specified should throw Notification Exception`() {
        val expectedVehicle = VehicleID.from("123")
        val aChecklist = Checklist.create(expectedVehicle)
        val expectedChecklistItemDescription =
            "This backend project was meticulously engineered with a strong focus on scalability, modular design, performance optimization, and maintainability. It ensures secure communication, robust data handling, and seamless integration across distributed systems..."
        val expectedChecklistItemPhotoUrl = "photoUrl"
        val expectedErrorMessage = "Failed to add a checklist item"
        val expectedConstraintError = "'description' of checklist item must be between 5 and 255 characters"
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            aChecklist.addItem(expectedChecklistItemDescription, expectedChecklistItemPhotoUrl)
        }

        assertAll(
            "Checklist exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedConstraintError, actualException.getErrors().first().message) },
        )
    }

    @Test
    fun `given a valid Checklist when call addItem with an empty checklist description should throw Notification Exception`() {
        val expectedVehicle = VehicleID.from("123")
        val aChecklist = Checklist.create(expectedVehicle)
        val expectedChecklistItemDescription = ""
        val expectedChecklistItemPhotoUrl = "photoUrl"
        val expectedErrorMessage = "Failed to add a checklist item"
        val expectedConstraintError = "'description' of checklist item cannot be empty"
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            aChecklist.addItem(expectedChecklistItemDescription, expectedChecklistItemPhotoUrl)
        }

        assertAll(
            "Checklist exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedConstraintError, actualException.getErrors().first().message) },
        )
    }

    @Test
    fun `given a valid Checklist when call addItem with a blank checklist description should throw Notification Exception`() {
        val expectedVehicle = VehicleID.from("123")
        val aChecklist = Checklist.create(expectedVehicle)
        val expectedChecklistItemDescription = "   "
        val expectedChecklistItemPhotoUrl = "photoUrl"
        val expectedErrorMessage = "Failed to add a checklist item"
        val expectedConstraintError = "'description' of checklist item cannot be blank"
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            aChecklist.addItem(expectedChecklistItemDescription, expectedChecklistItemPhotoUrl)
        }

        assertAll(
            "Checklist exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedConstraintError, actualException.getErrors().first().message) },
        )
    }

    @Test
    fun `given a valid Checklist when call addItem with checklist description less chars than specified should throw Notification Exception`() {
        val expectedVehicle = VehicleID.from("123")
        val aChecklist = Checklist.create(expectedVehicle)
        val expectedChecklistItemDescription = "Pass"
        val expectedChecklistItemPhotoUrl = "photoUrl"
        val expectedErrorMessage = "Failed to add a checklist item"
        val expectedConstraintError = "'description' of checklist item must be between 5 and 255 characters"
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            aChecklist.addItem(expectedChecklistItemDescription, expectedChecklistItemPhotoUrl)
        }

        assertAll(
            "Checklist exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedConstraintError, actualException.getErrors().first().message) },
        )
    }

    @Test
    fun `given a valid Checklist when call addItem with an empty checklist photoUrl should throw Notification Exception`() {
        val expectedVehicle = VehicleID.from("123")
        val aChecklist = Checklist.create(expectedVehicle)
        val expectedChecklistItemDescription = "Passenger window broken"
        val expectedChecklistItemPhotoUrl = ""
        val expectedErrorMessage = "Failed to add a checklist item"
        val expectedConstraintError = "'photoUrl' of checklist item cannot be empty"
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            aChecklist.addItem(expectedChecklistItemDescription, expectedChecklistItemPhotoUrl)
        }

        assertAll(
            "Checklist exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedConstraintError, actualException.getErrors().first().message) },
        )
    }

    @Test
    fun `given a valid Checklist when call addItem with a blank checklist photoUrl should throw Notification Exception`() {
        val expectedVehicle = VehicleID.from("123")
        val aChecklist = Checklist.create(expectedVehicle)
        val expectedChecklistItemDescription = "Passenger window broken"
        val expectedChecklistItemPhotoUrl = "   "
        val expectedErrorMessage = "Failed to add a checklist item"
        val expectedConstraintError = "'photoUrl' of checklist item cannot be blank"
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            aChecklist.addItem(expectedChecklistItemDescription, expectedChecklistItemPhotoUrl)
        }

        assertAll(
            "Checklist exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedConstraintError, actualException.getErrors().first().message) },
        )
    }

    @Test
    fun `given a valid Checklist when call updateItems should return Checklist with the new items`() {
        val expectedVehicle = VehicleID.from("123")
        val expectedCustomerSignature = null
        val expectedStatus = ChecklistStatus.PENDING
        val aChecklist = Checklist.create(expectedVehicle)
        val actualUpdatedAt = aChecklist.updatedAt
        val expectedChecklistItem1Description = "Passenger side mirror broken"
        val expectedChecklistItem1PhotoUrl = "photoUrl"
        val expectedChecklistItem2Description = "Front-lip scratched"
        val expectedChecklistItem2PhotoUrl = "photoUrl2"
        val expectedChecklistID = aChecklist.getId()
        val expectedItemsCount = 2

        val expectedChecklistItems = listOf(
            ChecklistItem.create(
                expectedChecklistItem1Description, expectedChecklistItem1PhotoUrl, expectedChecklistID
            ), ChecklistItem.create(
                expectedChecklistItem2Description, expectedChecklistItem2PhotoUrl, expectedChecklistID
            )
        )

        Thread.sleep(1) // To ensure that the updatedAt is different from createdAt
        aChecklist.updateItems(expectedChecklistItems)

        assertAll(
            "Checklist validation",
            { assertNotNull(aChecklist.getId()) },
            { assertEquals(expectedVehicle, aChecklist.vehicleID) },
            { assertEquals(expectedCustomerSignature, aChecklist.customerSignature) },
            { assertEquals(expectedStatus, aChecklist.status) },
            { assertEquals(expectedItemsCount, aChecklist.items.size) },
            { assertTrue(aChecklist.items.containsAll(expectedChecklistItems)) },
            { assertTrue(aChecklist.createdAt.isBefore(aChecklist.updatedAt)) },
            { assertTrue(aChecklist.updatedAt.isAfter(actualUpdatedAt)) })
    }

    @Test
    fun `given a valid Checklist when call updateItems with a null photoUrl should be Ok`() {
        val expectedVehicle = VehicleID.from("123")
        val expectedCustomerSignature = null
        val expectedStatus = ChecklistStatus.PENDING
        val aChecklist = Checklist.create(expectedVehicle)
        val actualUpdatedAt = aChecklist.updatedAt
        val expectedChecklistItem1Description = "Passenger side mirror broken"
        val expectedChecklistItem2Description = "Front-lip scratched"
        val expectedChecklistID = aChecklist.getId()
        val expectedItemsCount = 2

        val expectedChecklistItems = listOf(
            ChecklistItem.create(
                expectedChecklistItem1Description, null, expectedChecklistID
            ), ChecklistItem.create(
                expectedChecklistItem2Description, null, expectedChecklistID
            )
        )

        aChecklist.updateItems(expectedChecklistItems)

        assertAll(
            "Checklist validation",
            { assertNotNull(aChecklist.getId()) },
            { assertEquals(expectedVehicle, aChecklist.vehicleID) },
            { assertEquals(expectedCustomerSignature, aChecklist.customerSignature) },
            { assertEquals(expectedStatus, aChecklist.status) },
            { assertEquals(expectedItemsCount, aChecklist.items.size) },
            { assertTrue(aChecklist.items.containsAll(expectedChecklistItems)) },
            { assertTrue(aChecklist.createdAt.isBefore(aChecklist.updatedAt)) },
            { assertTrue(aChecklist.updatedAt.isAfter(actualUpdatedAt)) })
    }

    @Test
    fun `given a valid Checklist when call updateItems with checklist description more chars than specified should throw Notification Exception`() {
        val expectedVehicle = VehicleID.from("123")
        val aChecklist = Checklist.create(expectedVehicle)
        val expectedChecklistItemDescription =
            "This backend project was meticulously engineered with a strong focus on scalability, modular design, performance optimization, and maintainability. It ensures secure communication, robust data handling, and seamless integration across distributed systems..."
        val expectedChecklistItemPhotoUrl = "photoUrl"
        val expectedErrorMessage = "Failed to update the checklist items"
        val expectedConstraintError = "'description' of checklist item must be between 5 and 255 characters"
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            aChecklist.updateItems(
                listOf(
                    ChecklistItem.create(
                        expectedChecklistItemDescription, expectedChecklistItemPhotoUrl, aChecklist.getId()
                    )
                )
            )
        }

        assertAll(
            "Checklist exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedConstraintError, actualException.getErrors().first().message) },
        )
    }

    @Test
    fun `given a valid Checklist when call updateItems with an empty checklist description should throw Notification Exception`() {
        val expectedVehicle = VehicleID.from("123")
        val aChecklist = Checklist.create(expectedVehicle)
        val expectedChecklistItemDescription = ""
        val expectedChecklistItemPhotoUrl = "photoUrl"
        val expectedErrorMessage = "Failed to update the checklist items"
        val expectedConstraintError = "'description' of checklist item cannot be empty"
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            aChecklist.updateItems(
                listOf(
                    ChecklistItem.create(
                        expectedChecklistItemDescription, expectedChecklistItemPhotoUrl, aChecklist.getId()
                    )
                )
            )
        }

        assertAll(
            "Checklist exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedConstraintError, actualException.getErrors().first().message) },
        )
    }

    @Test
    fun `given a valid Checklist when call updateItem with a blank checklist description should throw Notification Exception`() {
        val expectedVehicle = VehicleID.from("123")
        val aChecklist = Checklist.create(expectedVehicle)
        val expectedChecklistItemDescription = "   "
        val expectedChecklistItemPhotoUrl = "photoUrl"
        val expectedErrorMessage = "Failed to update the checklist items"
        val expectedConstraintError = "'description' of checklist item cannot be blank"
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            aChecklist.updateItems(
                listOf(
                    ChecklistItem.create(
                        expectedChecklistItemDescription, expectedChecklistItemPhotoUrl, aChecklist.getId()
                    )
                )
            )
        }

        assertAll(
            "Checklist exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedConstraintError, actualException.getErrors().first().message) },
        )
    }

    @Test
    fun `given a valid Checklist when call updateItem with checklist description less chars than specified should throw Notification Exception`() {
        val expectedVehicle = VehicleID.from("123")
        val aChecklist = Checklist.create(expectedVehicle)
        val expectedChecklistItemDescription = "Pass"
        val expectedChecklistItemPhotoUrl = "photoUrl"
        val expectedErrorMessage = "Failed to update the checklist items"
        val expectedConstraintError = "'description' of checklist item must be between 5 and 255 characters"
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            aChecklist.updateItems(
                listOf(
                    ChecklistItem.create(
                        expectedChecklistItemDescription, expectedChecklistItemPhotoUrl, aChecklist.getId()
                    )
                )
            )
        }

        assertAll(
            "Checklist exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedConstraintError, actualException.getErrors().first().message) },
        )
    }

    @Test
    fun `given a valid Checklist when call updateItem with an empty checklist photoUrl should throw Notification Exception`() {
        val expectedVehicle = VehicleID.from("123")
        val aChecklist = Checklist.create(expectedVehicle)
        val expectedChecklistItemDescription = "Passenger window broken"
        val expectedChecklistItemPhotoUrl = ""
        val expectedErrorMessage = "Failed to update the checklist items"
        val expectedConstraintError = "'photoUrl' of checklist item cannot be empty"
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            aChecklist.updateItems(
                listOf(
                    ChecklistItem.create(
                        expectedChecklistItemDescription, expectedChecklistItemPhotoUrl, aChecklist.getId()
                    )
                )
            )
        }

        assertAll(
            "Checklist exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedConstraintError, actualException.getErrors().first().message) },
        )
    }

    @Test
    fun `given a valid Checklist when call updateItem with a blank checklist photoUrl should throw Notification Exception`() {
        val expectedVehicle = VehicleID.from("123")
        val aChecklist = Checklist.create(expectedVehicle)
        val expectedChecklistItemDescription = "Passenger window broken"
        val expectedChecklistItemPhotoUrl = "   "
        val expectedErrorMessage = "Failed to update the checklist items"
        val expectedConstraintError = "'photoUrl' of checklist item cannot be blank"
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            aChecklist.updateItems(
                listOf(
                    ChecklistItem.create(
                        expectedChecklistItemDescription, expectedChecklistItemPhotoUrl, aChecklist.getId()
                    )
                )
            )
        }

        assertAll(
            "Checklist exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedConstraintError, actualException.getErrors().first().message) },
        )
    }

    @Test
    fun `given a valid params when call sign then should return Checklist with a signature`() {
        val expectedVehicle = VehicleID.from("123")
        val expectedCustomerSignature = "signature"
        val expectedStatus = ChecklistStatus.SIGNED
        val expectedChecklistItems = emptyList<ChecklistItem>()

        var aChecklist = Checklist.create(expectedVehicle)
        val actualUpdatedAt = aChecklist.updatedAt

        assertEquals(ChecklistStatus.PENDING, aChecklist.status)
        Thread.sleep(1) // To ensure that the updatedAt is different from createdAt
        aChecklist = aChecklist.sign(expectedCustomerSignature)

        assertAll(
            "Checklist validation",
            { assertNotNull(aChecklist.getId()) },
            { assertEquals(expectedVehicle, aChecklist.vehicleID) },
            { assertEquals(expectedCustomerSignature, aChecklist.customerSignature) },
            { assertEquals(expectedStatus, aChecklist.status) },
            { assertEquals(expectedChecklistItems, aChecklist.items) },
            { assertTrue(aChecklist.createdAt.isBefore(aChecklist.updatedAt)) },
            { assertTrue(actualUpdatedAt.isBefore(aChecklist.updatedAt)) })
    }

    @Test
    fun `given a valid params when call sign with an empty signature should throw Notification Exception`() {
        val expectedVehicle = VehicleID.from("123")
        val expectedCustomerSignature = ""
        val expectedErrorMessage = "Failed to sign the checklist"
        val expectedConstraintError = "'customerSignature' of checklist cannot be empty"
        val expectedErrorCount = 1

        val aChecklist = Checklist.create(expectedVehicle)

        assertEquals(ChecklistStatus.PENDING, aChecklist.status)

        val actualException = assertThrows<NotificationException> {
            aChecklist.sign(expectedCustomerSignature)
        }

        assertAll(
            "Checklist exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedConstraintError, actualException.getErrors().first().message) },
        )
    }

    @Test
    fun `given a valid Checklist when call update then should update successfully`() {
        val expectedVehicle = VehicleID.from("123")
        val expectedCustomerSignature = "signature"
        val expectedStatus = ChecklistStatus.NOT_OK
        val expectedChecklistItem1Description = "Passenger side mirror broken"
        val expectedChecklistItem1PhotoUrl = "photoUrl"
        val expectedChecklistItem2Description = "Front-lip scratched"
        val expectedChecklistItem2PhotoUrl = "photoUrl2"
        val expectedChecklistID = ChecklistID.unique()
        val expectedItemsCount = 2

        val aChecklist = Checklist.create(expectedVehicle)
        aChecklist.addItem(expectedChecklistItem1Description, expectedChecklistItem1PhotoUrl)
        aChecklist.addItem(expectedChecklistItem2Description, expectedChecklistItem2PhotoUrl)

        assertEquals(ChecklistStatus.PENDING, aChecklist.status)

        Thread.sleep(1) // To ensure that the updatedAt is different from createdAt
        aChecklist.update(
            expectedCustomerSignature, expectedStatus, listOf(
                ChecklistItem.create(
                    expectedChecklistItem1Description, expectedChecklistItem1PhotoUrl, expectedChecklistID
                ), ChecklistItem.create(
                    expectedChecklistItem2Description, expectedChecklistItem2PhotoUrl, expectedChecklistID
                )
            )
        )

        assertAll(
            "Checklist validation",
            { assertNotNull(aChecklist.getId()) },
            { assertEquals(expectedVehicle, aChecklist.vehicleID) },
            { assertEquals(expectedCustomerSignature, aChecklist.customerSignature) },
            { assertEquals(expectedStatus, aChecklist.status) },
            { assertEquals(expectedItemsCount, aChecklist.items.size) },
            { assertTrue(aChecklist.createdAt.isBefore(aChecklist.updatedAt)) },
            { assertTrue(aChecklist.updatedAt.isAfter(aChecklist.createdAt)) })
    }

}