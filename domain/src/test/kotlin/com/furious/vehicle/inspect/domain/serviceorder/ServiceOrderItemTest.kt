package com.furious.vehicle.inspect.domain.serviceorder

import com.furious.vehicle.inspect.domain.exceptions.NotificationException
import com.furious.vehicle.inspect.domain.service.Service
import com.furious.vehicle.inspect.domain.servicecategory.ServiceCategoryID
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal

class ServiceOrderItemTest {

    @Test
    fun `given a valid params without overwrite service description and price when call create then instantiate a ServiceOrderItem`() {
        val expectedServiceOrder = ServiceOrderID.from("123")
        val expectedCategory = ServiceCategoryID.from("123")
        val expectedServiceDefaultPrice = BigDecimal("120.00")
        val expectedItemsQuantity = 1
        val expectedServiceDefaultDescription = "Oil Change"
        val expectedService =
            Service.create(expectedServiceDefaultDescription, expectedCategory, expectedServiceDefaultPrice)

        val aServiceOrderItem = ServiceOrderItem.create(
            expectedServiceOrder, expectedService, aQuantity = expectedItemsQuantity
        )

        assertAll(
            "Service Order Item validation",
            { assertNotNull(aServiceOrderItem.getId()) },
            { assertEquals(expectedServiceOrder, aServiceOrderItem.serviceOrder) },
            { assertEquals(expectedCategory, aServiceOrderItem.service.category) },
            { assertEquals(expectedServiceDefaultPrice, aServiceOrderItem.price) },
            { assertEquals(expectedServiceDefaultPrice, aServiceOrderItem.subTotal) },
            { assertEquals(expectedItemsQuantity, aServiceOrderItem.quantity) },
            { assertEquals(BigDecimal.ZERO, aServiceOrderItem.discountPercentage) },
            { assertEquals(expectedServiceDefaultDescription, aServiceOrderItem.serviceDescription) },
            { assertFalse(aServiceOrderItem.completed) },
            { assertNull(aServiceOrderItem.completedAt) })
    }

    @Test
    fun `given a valid params with discount and without overwrite service description and price when call create then instantiate a ServiceOrderItem`() {
        val expectedServiceOrder = ServiceOrderID.from("123")
        val expectedCategory = ServiceCategoryID.from("123")
        val expectedServiceDefaultPrice = BigDecimal("120.00")
        val expectedDiscountPercentage = BigDecimal("10.00")
        val expectedItemsQuantity = 1
        val expectedSubTotal = BigDecimal("108.00").multiply(BigDecimal(expectedItemsQuantity))
        val expectedServiceDefaultDescription = "Oil Change"
        val expectedService =
            Service.create(expectedServiceDefaultDescription, expectedCategory, expectedServiceDefaultPrice)

        val aServiceOrderItem = ServiceOrderItem.create(
            expectedServiceOrder,
            expectedService,
            aDiscountPercentage = expectedDiscountPercentage,
            aQuantity = expectedItemsQuantity
        )

        assertAll(
            "Service Order Item validation",
            { assertNotNull(aServiceOrderItem.getId()) },
            { assertEquals(expectedServiceOrder, aServiceOrderItem.serviceOrder) },
            { assertEquals(expectedCategory, aServiceOrderItem.service.category) },
            { assertEquals(expectedServiceDefaultPrice, aServiceOrderItem.price) },
            { assertEquals(expectedDiscountPercentage, aServiceOrderItem.discountPercentage) },
            { assertEquals(expectedItemsQuantity, aServiceOrderItem.quantity) },
            { assertEquals(expectedSubTotal, aServiceOrderItem.subTotal) },
            { assertEquals(expectedServiceDefaultDescription, aServiceOrderItem.serviceDescription) },
            { assertFalse(aServiceOrderItem.completed) },
            { assertNull(aServiceOrderItem.completedAt) })
    }

    @Test
    fun `given a valid params with discount and 2 items without overwrite service description and price when call create then instantiate a ServiceOrderItem`() {
        val expectedServiceOrder = ServiceOrderID.from("123")
        val expectedCategory = ServiceCategoryID.from("123")
        val expectedServiceDefaultPrice = BigDecimal("120.00")
        val expectedDiscountPercentage = BigDecimal("10.00")
        val expectedItemsQuantity = 2
        val expectedSubTotal = BigDecimal("108.00").multiply(BigDecimal(expectedItemsQuantity))
        val expectedServiceDefaultDescription = "Oil Change"
        val expectedService =
            Service.create(expectedServiceDefaultDescription, expectedCategory, expectedServiceDefaultPrice)

        val aServiceOrderItem = ServiceOrderItem.create(
            expectedServiceOrder,
            expectedService,
            aDiscountPercentage = expectedDiscountPercentage,
            aQuantity = expectedItemsQuantity
        )

        assertAll(
            "Service Order Item validation",
            { assertNotNull(aServiceOrderItem.getId()) },
            { assertEquals(expectedServiceOrder, aServiceOrderItem.serviceOrder) },
            { assertEquals(expectedCategory, aServiceOrderItem.service.category) },
            { assertEquals(expectedServiceDefaultPrice, aServiceOrderItem.price) },
            { assertEquals(expectedDiscountPercentage, aServiceOrderItem.discountPercentage) },
            { assertEquals(expectedItemsQuantity, aServiceOrderItem.quantity) },
            { assertEquals(expectedSubTotal, aServiceOrderItem.subTotal) },
            { assertEquals(expectedServiceDefaultDescription, aServiceOrderItem.serviceDescription) },
            { assertFalse(aServiceOrderItem.completed) },
            { assertNull(aServiceOrderItem.completedAt) })
    }

    @Test
    fun `given a valid params overriding service default description and price when call create then instantiate a ServiceOrderItem`() {
        val expectedServiceOrder = ServiceOrderID.from("123")
        val expectedCategory = ServiceCategoryID.from("123")
        val expectedServiceDefaultPrice = BigDecimal("120.00")
        val expectedServiceDefaultDescription = "Oil Change"
        val expectedItemsQuantity = 1
        val expectedService = Service.create("test", expectedCategory, BigDecimal("80.00"))

        val aServiceOrderItem = ServiceOrderItem.create(
            expectedServiceOrder,
            expectedService,
            expectedServiceDefaultDescription,
            expectedServiceDefaultPrice,
            aQuantity = expectedItemsQuantity
        )

        assertAll(
            "Service Order Item validation",
            { assertNotNull(aServiceOrderItem.getId()) },
            { assertEquals(expectedServiceOrder, aServiceOrderItem.serviceOrder) },
            { assertEquals(expectedCategory, aServiceOrderItem.service.category) },
            { assertEquals(expectedServiceDefaultPrice, aServiceOrderItem.price) },
            { assertEquals(expectedItemsQuantity, aServiceOrderItem.quantity) },
            { assertEquals(expectedServiceDefaultPrice, aServiceOrderItem.subTotal) },
            { assertEquals(BigDecimal.ZERO, aServiceOrderItem.discountPercentage) },
            { assertEquals(expectedServiceDefaultDescription, aServiceOrderItem.serviceDescription) },
            { assertFalse(aServiceOrderItem.completed) },
            { assertNull(aServiceOrderItem.completedAt) })
    }

    @Test
    fun `given a valid params with discount and overriding service default description and price when call create then instantiate a ServiceOrderItem`() {
        val expectedServiceOrder = ServiceOrderID.from("123")
        val expectedCategory = ServiceCategoryID.from("123")
        val expectedServiceFinalPrice = BigDecimal("120.00")
        val expectedDiscountPercentage = BigDecimal("10.00")
        val expectedItemsQuantity = 1
        val expectedSubTotal = BigDecimal("108.00").multiply(BigDecimal(expectedItemsQuantity))
        val expectedServiceDefaultDescription = "Oil Change"
        val expectedService = Service.create("test", expectedCategory, BigDecimal("80.00"))

        val aServiceOrderItem = ServiceOrderItem.create(
            expectedServiceOrder,
            expectedService,
            expectedServiceDefaultDescription,
            expectedServiceFinalPrice,
            expectedDiscountPercentage,
            aQuantity = expectedItemsQuantity
        )

        assertAll(
            "Service Order Item validation",
            { assertNotNull(aServiceOrderItem.getId()) },
            { assertEquals(expectedServiceOrder, aServiceOrderItem.serviceOrder) },
            { assertEquals(expectedCategory, aServiceOrderItem.service.category) },
            { assertEquals(expectedServiceFinalPrice, aServiceOrderItem.price) },
            { assertEquals(expectedDiscountPercentage, aServiceOrderItem.discountPercentage) },
            { assertEquals(expectedSubTotal, aServiceOrderItem.subTotal) },
            { assertEquals(expectedServiceDefaultDescription, aServiceOrderItem.serviceDescription) },
            { assertFalse(aServiceOrderItem.completed) },
            { assertNull(aServiceOrderItem.completedAt) })
    }

    @Test
    fun `given a valid params overriding service default description when call create then instantiate a ServiceOrderItem`() {
        val expectedServiceOrder = ServiceOrderID.from("123")
        val expectedCategory = ServiceCategoryID.from("123")
        val expectedServiceFinalPrice = BigDecimal("120.00")
        val expectedServiceFinalDescription = "Oil Change"
        val expectedItemsQuantity = 1
        val expectedService = Service.create("test", expectedCategory, expectedServiceFinalPrice)

        val aServiceOrderItem = ServiceOrderItem.create(
            expectedServiceOrder,
            expectedService,
            expectedServiceFinalDescription,
            null,
            aQuantity = expectedItemsQuantity
        )

        assertAll(
            "Service Order Item validation",
            { assertNotNull(aServiceOrderItem.getId()) },
            { assertEquals(expectedServiceOrder, aServiceOrderItem.serviceOrder) },
            { assertEquals(expectedCategory, aServiceOrderItem.service.category) },
            { assertEquals(expectedServiceFinalPrice, aServiceOrderItem.price) },
            { assertEquals(expectedItemsQuantity, aServiceOrderItem.quantity) },
            { assertEquals(expectedServiceFinalPrice, aServiceOrderItem.subTotal) },
            { assertEquals(BigDecimal.ZERO, aServiceOrderItem.discountPercentage) },
            { assertEquals(expectedServiceFinalDescription, aServiceOrderItem.serviceDescription) },
            { assertFalse(aServiceOrderItem.completed) },
            { assertNull(aServiceOrderItem.completedAt) })
    }

    @Test
    fun `given a valid params overriding service default price when call create then instantiate a ServiceOrderItem`() {
        val expectedServiceOrder = ServiceOrderID.from("123")
        val expectedCategory = ServiceCategoryID.from("123")
        val expectedServiceDefaultPrice = BigDecimal("120.00")
        val expectedItemsQuantity = 1
        val expectedServiceDefaultDescription = "Oil Change"
        val expectedService = Service.create(expectedServiceDefaultDescription, expectedCategory, BigDecimal("80.00"))

        val aServiceOrderItem = ServiceOrderItem.create(
            expectedServiceOrder, expectedService, null, expectedServiceDefaultPrice, aQuantity = 1
        )

        assertAll(
            "Service Order Item validation",
            { assertNotNull(aServiceOrderItem.getId()) },
            { assertEquals(expectedServiceOrder, aServiceOrderItem.serviceOrder) },
            { assertEquals(expectedCategory, aServiceOrderItem.service.category) },
            { assertEquals(expectedServiceDefaultPrice, aServiceOrderItem.price) },
            { assertEquals(expectedItemsQuantity, aServiceOrderItem.quantity) },
            { assertEquals(expectedServiceDefaultPrice, aServiceOrderItem.subTotal) },
            { assertEquals(BigDecimal.ZERO, aServiceOrderItem.discountPercentage) },
            { assertEquals(expectedServiceDefaultDescription, aServiceOrderItem.serviceDescription) },
            { assertFalse(aServiceOrderItem.completed) },
            { assertNull(aServiceOrderItem.completedAt) })
    }

    @Test
    fun `given a valid params with discount and overriding default price when call create then instantiate a ServiceOrderItem`() {
        val expectedServiceOrder = ServiceOrderID.from("123")
        val expectedCategory = ServiceCategoryID.from("123")
        val expectedServiceFinalPrice = BigDecimal("120.00")
        val expectedDiscountPercentage = BigDecimal("10.00")
        val expectedItemsQuantity = 1
        val expectedSubTotal = BigDecimal("108.00").multiply(BigDecimal(expectedItemsQuantity))
        val expectedServiceDefaultDescription = "Oil Change"
        val expectedService = Service.create(expectedServiceDefaultDescription, expectedCategory, BigDecimal("80.00"))

        val aServiceOrderItem = ServiceOrderItem.create(
            expectedServiceOrder,
            expectedService,
            aPrice = expectedServiceFinalPrice,
            aDiscountPercentage = expectedDiscountPercentage,
            aQuantity = expectedItemsQuantity
        )

        assertAll(
            "Service Order Item validation",
            { assertNotNull(aServiceOrderItem.getId()) },
            { assertEquals(expectedServiceOrder, aServiceOrderItem.serviceOrder) },
            { assertEquals(expectedCategory, aServiceOrderItem.service.category) },
            { assertEquals(expectedServiceFinalPrice, aServiceOrderItem.price) },
            { assertEquals(expectedItemsQuantity, aServiceOrderItem.quantity) },
            { assertEquals(expectedDiscountPercentage, aServiceOrderItem.discountPercentage) },
            { assertEquals(expectedSubTotal, aServiceOrderItem.subTotal) },
            { assertEquals(expectedServiceDefaultDescription, aServiceOrderItem.serviceDescription) },
            { assertFalse(aServiceOrderItem.completed) },
            { assertNull(aServiceOrderItem.completedAt) })
    }

    @Test
    fun `given a valid params with discount and 2 items overriding default price when call create then instantiate a ServiceOrderItem`() {
        val expectedServiceOrder = ServiceOrderID.from("123")
        val expectedCategory = ServiceCategoryID.from("123")
        val expectedServiceFinalPrice = BigDecimal("120.00")
        val expectedDiscountPercentage = BigDecimal("10.00")
        val expectedItemsQuantity = 2
        val expectedSubTotal = BigDecimal("108.00").multiply(BigDecimal(expectedItemsQuantity))
        val expectedServiceDefaultDescription = "Oil Change"
        val expectedService = Service.create(expectedServiceDefaultDescription, expectedCategory, BigDecimal("80.00"))

        val aServiceOrderItem = ServiceOrderItem.create(
            expectedServiceOrder,
            expectedService,
            aPrice = expectedServiceFinalPrice,
            aDiscountPercentage = expectedDiscountPercentage,
            aQuantity = expectedItemsQuantity
        )

        assertAll(
            "Service Order Item validation",
            { assertNotNull(aServiceOrderItem.getId()) },
            { assertEquals(expectedServiceOrder, aServiceOrderItem.serviceOrder) },
            { assertEquals(expectedCategory, aServiceOrderItem.service.category) },
            { assertEquals(expectedServiceFinalPrice, aServiceOrderItem.price) },
            { assertEquals(expectedItemsQuantity, aServiceOrderItem.quantity) },
            { assertEquals(expectedDiscountPercentage, aServiceOrderItem.discountPercentage) },
            { assertEquals(expectedSubTotal, aServiceOrderItem.subTotal) },
            { assertEquals(expectedServiceDefaultDescription, aServiceOrderItem.serviceDescription) },
            { assertFalse(aServiceOrderItem.completed) },
            { assertNull(aServiceOrderItem.completedAt) })
    }

    @Test
    fun `given a ServiceOrderItem when call complete then set completed to true and completedAt to now`() {
        val expectedServiceOrder = ServiceOrderID.from("123")
        val expectedCategory = ServiceCategoryID.from("123")
        val expectedServiceDefaultPrice = BigDecimal("120.00")
        val expectedItemsQuantity = 1
        val expectedServiceDefaultDescription = "Oil Change"
        val expectedService =
            Service.create(expectedServiceDefaultDescription, expectedCategory, expectedServiceDefaultPrice)

        val aServiceOrderItem = ServiceOrderItem.create(
            expectedServiceOrder, expectedService, aQuantity = expectedItemsQuantity
        )

        assertFalse(aServiceOrderItem.completed)
        assertNull(aServiceOrderItem.completedAt)

        aServiceOrderItem.complete()

        assertAll(
            "Service Order Item validation",
            { assertNotNull(aServiceOrderItem.getId()) },
            { assertEquals(expectedServiceOrder, aServiceOrderItem.serviceOrder) },
            { assertEquals(expectedCategory, aServiceOrderItem.service.category) },
            { assertEquals(expectedServiceDefaultPrice, aServiceOrderItem.price) },
            { assertEquals(expectedItemsQuantity, aServiceOrderItem.quantity) },
            { assertEquals(BigDecimal.ZERO, aServiceOrderItem.discountPercentage) },
            { assertEquals(expectedServiceDefaultPrice, aServiceOrderItem.subTotal) },
            { assertEquals(expectedServiceDefaultDescription, aServiceOrderItem.serviceDescription) },
            { assertTrue(aServiceOrderItem.completed) },
            { assertNotNull(aServiceOrderItem.completedAt) })
    }

    @Test
    fun `given an invalid ServiceOrderItem with zero or minus items then should return Notification Exception`() {
        val expectedServiceOrder = ServiceOrderID.from("123")
        val expectedCategory = ServiceCategoryID.from("123")
        val expectedServiceDefaultPrice = BigDecimal("120.00")
        val expectedItemsQuantity = 0
        val expectedServiceDefaultDescription = "Oil Change"
        val expectedErrorMessage = "Failed to create an Entity Service Order Item"
        val expectedErrorMessageDetail = "'quantity' must be greater than 0"
        val expectedErrorCount = 1

        val expectedService =
            Service.create(expectedServiceDefaultDescription, expectedCategory, expectedServiceDefaultPrice)

        val actualException = assertThrows<NotificationException> {
            ServiceOrderItem.create(
                expectedServiceOrder, expectedService, aQuantity = expectedItemsQuantity
            )
        }

        assertAll(
            "Service Order Item exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedErrorMessageDetail, actualException.getErrors()[0].message) })

    }

    @Test
    fun `given an invalid ServiceOrderItem with price lower than zero then should return Notification Exception`() {
        val expectedServiceOrder = ServiceOrderID.from("123")
        val expectedCategory = ServiceCategoryID.from("123")
        val expectedServiceDefaultPrice = BigDecimal("120.00")
        val expectedItemsQuantity = 1
        val expectedServiceDefaultDescription = "Oil Change"
        val expectedErrorMessage = "Failed to create an Entity Service Order Item"
        val expectedErrorMessageDetail = "'price' must be greater than 0"
        val expectedErrorCount = 1

        val expectedService =
            Service.create(expectedServiceDefaultDescription, expectedCategory, expectedServiceDefaultPrice)

        val actualException = assertThrows<NotificationException> {
            ServiceOrderItem.create(
                expectedServiceOrder, expectedService, aPrice = BigDecimal("-100.00"), aQuantity = expectedItemsQuantity
            )
        }

        assertAll(
            "Service Order Item exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedErrorMessageDetail, actualException.getErrors()[0].message) })

    }

    @Test
    fun `given an invalid ServiceOrderItem with discount lower than zero then should return Notification Exception`() {
        val expectedServiceOrder = ServiceOrderID.from("123")
        val expectedCategory = ServiceCategoryID.from("123")
        val expectedServiceDefaultPrice = BigDecimal("120.00")
        val expectedServiceDiscount = BigDecimal("-10.00")
        val expectedItemsQuantity = 1
        val expectedServiceDefaultDescription = "Oil Change"
        val expectedErrorMessage = "Failed to create an Entity Service Order Item"
        val expectedErrorMessageDetail = "'discountPercentage' must be greater than or equal to 0"
        val expectedErrorCount = 1

        val expectedService =
            Service.create(expectedServiceDefaultDescription, expectedCategory, expectedServiceDefaultPrice)

        val actualException = assertThrows<NotificationException> {
            ServiceOrderItem.create(
                expectedServiceOrder,
                expectedService,
                aDiscountPercentage = expectedServiceDiscount,
                aQuantity = expectedItemsQuantity
            )
        }

        assertAll(
            "Service Order Item exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedErrorMessageDetail, actualException.getErrors()[0].message) })

    }

    @Test
    fun `given an invalid ServiceOrderItem with discount lower than zero and negative price then should return Notification Exception with 2 errors`() {
        val expectedServiceOrder = ServiceOrderID.from("123")
        val expectedCategory = ServiceCategoryID.from("123")
        val expectedServiceDefaultPrice = BigDecimal("120.00")
        val expectedServiceDiscount = BigDecimal("-10.00")
        val expectedItemsQuantity = 1
        val expectedServiceDefaultDescription = "Oil Change"
        val expectedErrorMessage = "Failed to create an Entity Service Order Item"
        val expectedErrorMessageDiscountConstraint = "'discountPercentage' must be greater than or equal to 0"
        val expectedErrorMessagePriceConstraint = "'price' must be greater than 0"
        val expectedErrorCount = 2

        val expectedService =
            Service.create(expectedServiceDefaultDescription, expectedCategory, expectedServiceDefaultPrice)

        val actualException = assertThrows<NotificationException> {
            ServiceOrderItem.create(
                expectedServiceOrder,
                expectedService,
                aPrice = BigDecimal("-100.00"),
                aDiscountPercentage = expectedServiceDiscount,
                aQuantity = expectedItemsQuantity
            )
        }

        assertAll(
            "Service Order Item exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedErrorMessagePriceConstraint, actualException.getErrors()[0].message) },
            { assertEquals(expectedErrorMessageDiscountConstraint, actualException.getErrors()[1].message) })

    }

    @Test
    fun `given a Service Order Item when call update then should return instance updated`() {
        val expectedServiceOrder = ServiceOrderID.from("123")
        val expectedCategory = ServiceCategoryID.from("123")
        val expectedServiceDefaultPrice = BigDecimal("120.00")
        val expectedServiceDescription = "Castrol 1L"
        val expectedItemPrice = BigDecimal("50.00")
        val expectedDiscountPercentage = BigDecimal("15.00")
        val expectedItemsQuantity = 1
        val expectedSubtotal = BigDecimal("42.50").multiply(BigDecimal(expectedItemsQuantity))
        val expectedServiceDefaultDescription = "Oil Change"
        val expectedService =
            Service.create(expectedServiceDefaultDescription, expectedCategory, expectedServiceDefaultPrice)

        val aServiceOrderItem = ServiceOrderItem.create(
            expectedServiceOrder,
            expectedService,
            "Motul 1L",
            BigDecimal("40.00"),
            aDiscountPercentage = BigDecimal("10.00"),
            aQuantity = 2
        )

        aServiceOrderItem.update(
            expectedService,
            expectedServiceDescription,
            expectedItemPrice,
            expectedDiscountPercentage,
            expectedItemsQuantity,
            false
        )

        assertAll(
            "Service Order Item validation",
            { assertNotNull(aServiceOrderItem.getId()) },
            { assertEquals(expectedServiceOrder, aServiceOrderItem.serviceOrder) },
            { assertEquals(expectedCategory, aServiceOrderItem.service.category) },
            { assertEquals(expectedItemPrice, aServiceOrderItem.price) },
            { assertEquals(expectedItemsQuantity, aServiceOrderItem.quantity) },
            { assertEquals(expectedDiscountPercentage, aServiceOrderItem.discountPercentage) },
            { assertEquals(expectedSubtotal, aServiceOrderItem.subTotal) },
            { assertEquals(expectedServiceDescription, aServiceOrderItem.serviceDescription) },
            { assertFalse(aServiceOrderItem.completed) },
            { assertNull(aServiceOrderItem.completedAt) })
    }

    @Test
    fun `given a Service Order Item when call update with completed set to true then should return instance with completedAt`() {
        val expectedServiceOrder = ServiceOrderID.from("123")
        val expectedCategory = ServiceCategoryID.from("123")
        val expectedServiceDefaultPrice = BigDecimal("120.00")
        val expectedServiceDescription = "Castrol 1L"
        val expectedItemPrice = BigDecimal("50.00")
        val expectedDiscountPercentage = BigDecimal("15.00")
        val expectedItemsQuantity = 1
        val expectedSubtotal = BigDecimal("42.50").multiply(BigDecimal(expectedItemsQuantity))
        val expectedServiceDefaultDescription = "Oil Change"
        val expectedService =
            Service.create(expectedServiceDefaultDescription, expectedCategory, expectedServiceDefaultPrice)

        val aServiceOrderItem = ServiceOrderItem.create(
            expectedServiceOrder,
            expectedService,
            "Motul 1L",
            BigDecimal("40.00"),
            aDiscountPercentage = BigDecimal("10.00"),
            aQuantity = 2
        )

        aServiceOrderItem.update(
            expectedService,
            expectedServiceDescription,
            expectedItemPrice,
            expectedDiscountPercentage,
            expectedItemsQuantity,
            true
        )

        assertAll(
            "Service Order Item validation",
            { assertNotNull(aServiceOrderItem.getId()) },
            { assertEquals(expectedServiceOrder, aServiceOrderItem.serviceOrder) },
            { assertEquals(expectedCategory, aServiceOrderItem.service.category) },
            { assertEquals(expectedItemPrice, aServiceOrderItem.price) },
            { assertEquals(expectedItemsQuantity, aServiceOrderItem.quantity) },
            { assertEquals(expectedDiscountPercentage, aServiceOrderItem.discountPercentage) },
            { assertEquals(expectedSubtotal, aServiceOrderItem.subTotal) },
            { assertEquals(expectedServiceDescription, aServiceOrderItem.serviceDescription) },
            { assertTrue(aServiceOrderItem.completed) },
            { assertNotNull(aServiceOrderItem.completedAt) })
    }

    @Test
    fun `given a Service Order Item when call update with an invalid quantity then should return Notification Exception`() {
        val expectedServiceOrder = ServiceOrderID.from("123")
        val expectedCategory = ServiceCategoryID.from("123")
        val expectedServiceDefaultPrice = BigDecimal("120.00")
        val expectedServiceDescription = "Castrol 1L"
        val expectedItemPrice = BigDecimal("50.00")
        val expectedDiscountPercentage = BigDecimal("15.00")
        val expectedItemsQuantity = 0
        val expectedServiceDefaultDescription = "Oil Change"
        val expectedErrorMessage = "Failed to update an Entity Service Order Item"
        val expectedErrorMessageDetail = "'quantity' must be greater than 0"
        val expectedErrorCount = 1

        val expectedService =
            Service.create(expectedServiceDefaultDescription, expectedCategory, expectedServiceDefaultPrice)

        val aServiceOrderItem = ServiceOrderItem.create(
            expectedServiceOrder,
            expectedService,
            "Motul 1L",
            BigDecimal("40.00"),
            aDiscountPercentage = BigDecimal("10.00"),
            aQuantity = 2
        )

        val actualException = assertThrows<NotificationException> {
            aServiceOrderItem.update(
                expectedService,
                expectedServiceDescription,
                expectedItemPrice,
                expectedDiscountPercentage,
                expectedItemsQuantity,
                false
            )
        }

        assertAll(
            "Service Order Item exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedErrorMessageDetail, actualException.getErrors()[0].message) })
    }

    @Test
    fun `given a Service Order Item when call update with an invalid negative price then should return Notification Exception`() {
        val expectedServiceOrder = ServiceOrderID.from("123")
        val expectedCategory = ServiceCategoryID.from("123")
        val expectedServiceDefaultPrice = BigDecimal("120.00")
        val expectedServiceDescription = "Castrol 1L"
        val expectedItemPrice = BigDecimal("-50.00")
        val expectedDiscountPercentage = BigDecimal("15.00")
        val expectedItemsQuantity = 1
        val expectedServiceDefaultDescription = "Oil Change"
        val expectedErrorMessage = "Failed to update an Entity Service Order Item"
        val expectedErrorMessageDetail = "'price' must be greater than 0"
        val expectedErrorCount = 1

        val expectedService =
            Service.create(expectedServiceDefaultDescription, expectedCategory, expectedServiceDefaultPrice)

        val aServiceOrderItem = ServiceOrderItem.create(
            expectedServiceOrder,
            expectedService,
            "Motul 1L",
            BigDecimal("40.00"),
            aDiscountPercentage = BigDecimal("10.00"),
            aQuantity = 2
        )

        val actualException = assertThrows<NotificationException> {
            aServiceOrderItem.update(
                expectedService,
                expectedServiceDescription,
                expectedItemPrice,
                expectedDiscountPercentage,
                expectedItemsQuantity,
                false
            )
        }

        assertAll(
            "Service Order Item exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedErrorMessageDetail, actualException.getErrors()[0].message) })
    }

    @Test
    fun `given a Service Order Item when call update with an invalid negative discount then should return Notification Exception`() {
        val expectedServiceOrder = ServiceOrderID.from("123")
        val expectedCategory = ServiceCategoryID.from("123")
        val expectedServiceDefaultPrice = BigDecimal("120.00")
        val expectedServiceDescription = "Castrol 1L"
        val expectedItemPrice = BigDecimal("50.00")
        val expectedDiscountPercentage = BigDecimal("-15.00")
        val expectedItemsQuantity = 1
        val expectedServiceDefaultDescription = "Oil Change"
        val expectedErrorMessage = "Failed to update an Entity Service Order Item"
        val expectedErrorMessageDetail = "'discountPercentage' must be greater than or equal to 0"
        val expectedErrorCount = 1

        val expectedService =
            Service.create(expectedServiceDefaultDescription, expectedCategory, expectedServiceDefaultPrice)

        val aServiceOrderItem = ServiceOrderItem.create(
            expectedServiceOrder,
            expectedService,
            "Motul 1L",
            BigDecimal("40.00"),
            aDiscountPercentage = BigDecimal("10.00"),
            aQuantity = 2
        )

        val actualException = assertThrows<NotificationException> {
            aServiceOrderItem.update(
                expectedService,
                expectedServiceDescription,
                expectedItemPrice,
                expectedDiscountPercentage,
                expectedItemsQuantity,
                false
            )
        }

        assertAll(
            "Service Order Item exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedErrorMessageDetail, actualException.getErrors()[0].message) })
    }

    @Test
    fun `given a Service Order Item when call update with an invalid negative price and discount then should return Notification Exception with 2 errors`() {
        val expectedServiceOrder = ServiceOrderID.from("123")
        val expectedCategory = ServiceCategoryID.from("123")
        val expectedServiceDefaultPrice = BigDecimal("120.00")
        val expectedServiceDescription = "Castrol 1L"
        val expectedItemPrice = BigDecimal("-50.00")
        val expectedDiscountPercentage = BigDecimal("-15.00")
        val expectedItemsQuantity = 1
        val expectedServiceDefaultDescription = "Oil Change"
        val expectedErrorMessage = "Failed to update an Entity Service Order Item"
        val expectedErrorMessageDiscountConstraint = "'discountPercentage' must be greater than or equal to 0"
        val expectedErrorMessagePriceConstraint = "'price' must be greater than 0"
        val expectedErrorCount = 2

        val expectedService =
            Service.create(expectedServiceDefaultDescription, expectedCategory, expectedServiceDefaultPrice)

        val aServiceOrderItem = ServiceOrderItem.create(
            expectedServiceOrder,
            expectedService,
            "Motul 1L",
            BigDecimal("40.00"),
            aDiscountPercentage = BigDecimal("10.00"),
            aQuantity = 2
        )

        val actualException = assertThrows<NotificationException> {
            aServiceOrderItem.update(
                expectedService,
                expectedServiceDescription,
                expectedItemPrice,
                expectedDiscountPercentage,
                expectedItemsQuantity,
                false
            )
        }

        assertAll(
            "Service Order Item exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedErrorMessagePriceConstraint, actualException.getErrors()[0].message) },
            { assertEquals(expectedErrorMessageDiscountConstraint, actualException.getErrors()[1].message) })
    }
}