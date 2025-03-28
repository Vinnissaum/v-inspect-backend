package com.furious.vehicle.inspect.domain.customer

import com.furious.vehicle.inspect.domain.customer.valueobject.Address
import com.furious.vehicle.inspect.domain.customer.valueobject.Document
import com.furious.vehicle.inspect.domain.customer.valueobject.DocumentType
import com.furious.vehicle.inspect.domain.exceptions.NotificationException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CustomerTest {

    @Test
    fun `given a valid params when call create then instantiate a Customer`() {
        val expectedName = "Viniceta"
        val expectedPhone = "(12) 99999-9999"
        val expectedEmail = "vinicius@mail.com"
        val expectedDocument = Document.create(DocumentType.CPF, "12345")

        val aCustomer = Customer.create(expectedName, expectedPhone, expectedEmail, expectedDocument)

        assertAll(
            "Customer validation",
            { assertNotNull(aCustomer.getId()) },
            { assertEquals(expectedName, aCustomer.name) },
            { assertEquals(expectedPhone, aCustomer.phone) },
            { assertEquals(expectedEmail, aCustomer.email) },
            { assertEquals(expectedDocument, aCustomer.document) },
            { assertNotNull(aCustomer.createdAt) },
            { assertNotNull(aCustomer.updatedAt) })
    }

    @Test
    fun `given a valid params with an address when call create then instantiate a Customer`() {
        val expectedName = "Viniceta"
        val expectedPhone = "(12) 99999-9999"
        val expectedEmail = "vinicius@mail.com"
        val expectedDocument = Document.create(DocumentType.CPF, "12345")
        val expectedAddress = Address.create(
            "rua 1", "são paulo", "são paulo", "2333-4353", "brazil", "14 A"
        )

        val aCustomer = Customer.create(expectedName, expectedPhone, expectedEmail, expectedDocument, expectedAddress)

        assertAll(
            "Customer validation",
            { assertNotNull(aCustomer.getId()) },
            { assertEquals(expectedName, aCustomer.name) },
            { assertEquals(expectedPhone, aCustomer.phone) },
            { assertEquals(expectedEmail, aCustomer.email) },
            { assertEquals(expectedDocument, aCustomer.document) },
            { assertEquals(expectedAddress, aCustomer.address) },
            { assertNotNull(aCustomer.createdAt) },
            { assertNotNull(aCustomer.updatedAt) })
    }

    @Test
    fun `given a blank name when call create then should throw NotificationException`() {
        val expectedName = "   "
        val expectedPhone = "(12) 99999-9999"
        val expectedEmail = "vinicius@mail.com"
        val expectedDocument = Document.create(DocumentType.CPF, "12345")
        val expectedErrorMessage = "Failed to create an Aggregate Customer"
        val expectedNameErrorMessage = "'name' cannot be blank"
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            Customer.create(expectedName, expectedPhone, expectedEmail, expectedDocument)
        }

        assertAll(
            "Customer exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedNameErrorMessage, actualException.getErrors().first().message) },
        )
    }

    @Test
    fun `given an empty name when call create then should throw NotificationException`() {
        val expectedName = ""
        val expectedPhone = "(12) 99999-9999"
        val expectedEmail = "vinicius@mail.com"
        val expectedDocument = Document.create(DocumentType.CPF, "12345")
        val expectedErrorMessage = "Failed to create an Aggregate Customer"
        val expectedNameErrorMessage = "'name' cannot be empty"
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            Customer.create(expectedName, expectedPhone, expectedEmail, expectedDocument)
        }

        assertAll(
            "Customer exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedNameErrorMessage, actualException.getErrors().first().message) },
        )
    }

    @Test
    fun `given a name length less than 3 when call create then should throw NotificationException`() {
        val expectedName = "Vi"
        val expectedPhone = "(12) 99999-9999"
        val expectedEmail = "vinicius@mail.com"
        val expectedDocument = Document.create(DocumentType.CPF, "12345")
        val expectedErrorMessage = "Failed to create an Aggregate Customer"
        val expectedNameErrorMessage = "'name' must be between 3 and 255 characters"
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            Customer.create(expectedName, expectedPhone, expectedEmail, expectedDocument)
        }

        assertAll(
            "Customer exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedNameErrorMessage, actualException.getErrors().first().message) },
        )
    }

    @Test
    fun `given a name length more than 255 when call create then should throw NotificationException`() {
        val expectedName =
            "Dado o fluxo de dados atual, o último pull request desse SCRUM corrigiu o bug da execução " + //
                    "de requisições effcientes na API. Fala pro cliente que o deploy automatizado no Heroku facilitou a " + //
                    "resolução de conflito do polimorfismo aplicado nas classes. Desde ontem a noite a compilação final do " + //
                    "programa causou o bug da execução de requisições effcientes na API."
        val expectedPhone = "(12) 99999-9999"
        val expectedEmail = "vinicius@mail.com"
        val expectedDocument = Document.create(DocumentType.CPF, "12345")
        val expectedErrorMessage = "Failed to create an Aggregate Customer"
        val expectedNameErrorMessage = "'name' must be between 3 and 255 characters"
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            Customer.create(expectedName, expectedPhone, expectedEmail, expectedDocument)
        }

        assertAll(
            "Customer exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedNameErrorMessage, actualException.getErrors().first().message) },
        )
    }

    @Test
    fun `given a name length less than 3 and an invalid phone when call create then should throw NotificationException`() {
        val expectedName = "Vi"
        val expectedPhone = "123"
        val expectedEmail = "vinicius@mail.com"
        val expectedDocument = Document.create(DocumentType.CPF, "12345")
        val expectedErrorMessage = "Failed to create an Aggregate Customer"
        val expectedNameErrorMessage = "'name' must be between 3 and 255 characters"
        val expectedPhoneErrorMessage = "invalid 'phone' format"
        val expectedErrorCount = 2

        val actualException = assertThrows<NotificationException> {
            Customer.create(expectedName, expectedPhone, expectedEmail, expectedDocument)
        }

        assertAll(
            "Customer exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedNameErrorMessage, actualException.getErrors()[0].message) },
            { assertEquals(expectedPhoneErrorMessage, actualException.getErrors()[1].message) },
        )
    }

    @Test
    fun `given an invalid email when call create then should throw NotificationException`() {
        val expectedName = "Viniceta"
        val expectedPhone = "(12) 99999-9999"
        val expectedEmail = "Viniceta.com"
        val expectedDocument = Document.create(DocumentType.CPF, "12345")
        val expectedErrorMessage = "Failed to create an Aggregate Customer"
        val expectedEmailErrorMessage = "invalid 'email'"
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            Customer.create(expectedName, expectedPhone, expectedEmail, expectedDocument)
        }

        assertAll(
            "Customer exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedEmailErrorMessage, actualException.getErrors().first().message) })
    }

    @Test
    fun `given an invalid document with an empty or blank value when call create then should throw NotificationException`() {
        val expectedName = "Viniceta"
        val expectedPhone = "(12) 99999-9999"
        val expectedEmail = "viniceta@mail.com"
        val expectedDocument = Document.create(DocumentType.CPF, "  ")
        val expectedErrorMessage = "Failed to create an Aggregate Customer"
        val expectedDocumentErrorMessage = "'document' value cannot be empty"
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            Customer.create(expectedName, expectedPhone, expectedEmail, expectedDocument)
        }

        assertAll(
            "Customer exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedDocumentErrorMessage, actualException.getErrors().first().message) })
    }

    @Test
    fun `given params with an invalid address and has some empty value when call create then should throw NotificationException`() {
        val expectedName = "Viniceta"
        val expectedPhone = "(12) 99999-9999"
        val expectedEmail = "vinicius@mail.com"
        val expectedDocument = Document.create(DocumentType.CPF, "12345")
        val expectedAddress = Address.create(
            "  ", "são paulo", "são paulo", "2333-4353", "brazil", "14 A"
        )
        val expectedErrorMessage = "Failed to create an Aggregate Customer"
        val expectedAddressErrorMessage = "'street' cannot be empty"
        val expectedErrorCount = 1

        val actualException = assertThrows<NotificationException> {
            Customer.create(expectedName, expectedPhone, expectedEmail, expectedDocument, expectedAddress)
        }

        assertAll(
            "Customer exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedAddressErrorMessage, actualException.getErrors().first().message) })
    }

    @Test
    fun `given a valid params when call update then return a Customer updated`() {
        val expectedName = "Viniceta"
        val expectedPhone = "(12) 99999-9999"
        val expectedEmail = "vinicius@mail.com"
        val expectedDocument = Document.create(DocumentType.CPF, "12345")

        val aCustomer = Customer.create( //
            "Vinicius", //
            "(12) 9999-9999", //
            "test@mail.com", //
            Document.create(DocumentType.RG, "12345") //
        )
        val aCreatedAt = aCustomer.createdAt
        val anUpdatedAt = aCustomer.updatedAt

        val actualCustomer = aCustomer.update(expectedName, expectedPhone, expectedEmail, expectedDocument)

        assertAll(
            "Customer validation",
            { assertEquals(expectedName, actualCustomer.name) },
            { assertEquals(expectedPhone, actualCustomer.phone) },
            { assertEquals(expectedEmail, actualCustomer.email) },
            { assertEquals(expectedDocument, actualCustomer.document) },
            { assertEquals(aCreatedAt, actualCustomer.createdAt) },
            { assertTrue(anUpdatedAt.isBefore(actualCustomer.updatedAt)) },
            { assertNull(actualCustomer.address) })
    }

    @Test
    fun `given a valid params when call update with an address then return a Customer updated`() {
        val expectedName = "Viniceta"
        val expectedPhone = "(12) 99999-9999"
        val expectedEmail = "vinicius@mail.com"
        val expectedDocument = Document.create(DocumentType.CPF, "12345")
        val expectedAddress = Address.create(
            "rua 1", "são paulo", "são paulo", "2333-4353", "brazil", "14 A"
        )

        val aCustomer = Customer.create( //
            "Vinicius", //
            "(12) 0000-0000", //
            "test@mail.com", //
            Document.create(DocumentType.RG, "12345") //
        )
        val aCreatedAt = aCustomer.createdAt
        val anUpdatedAt = aCustomer.updatedAt

        Thread.sleep(1L)
        val actualCustomer =
            aCustomer.update(expectedName, expectedPhone, expectedEmail, expectedDocument, expectedAddress)

        assertAll(
            "Customer validation",
            { assertNotNull(aCustomer.getId()) },
            { assertEquals(expectedName, actualCustomer.name) },
            { assertEquals(expectedPhone, actualCustomer.phone) },
            { assertEquals(expectedEmail, actualCustomer.email) },
            { assertEquals(expectedDocument, actualCustomer.document) },
            { assertEquals(aCreatedAt, actualCustomer.createdAt) },
            { assertTrue(anUpdatedAt.isBefore(actualCustomer.updatedAt)) },
            { assertEquals(expectedAddress, actualCustomer.address) })
    }

    @Test
    fun `given invalid valid params when call update then should throw NotificationException`() {
        val expectedName = "Viniceta"
        val expectedPhone = "(12) 99999-9999"
        val expectedEmail = "vinicius@mail.com"
        val expectedDocument = Document.create(DocumentType.CPF, "12345")
        val expectedAddress = Address.create(
            "  ", "são paulo", "são paulo", "2333-4353", "brazil", "14 A"
        )
        val expectedErrorMessage = "Failed to update an Aggregate Customer"
        val expectedAddressErrorMessage = "'street' cannot be empty"
        val expectedErrorCount = 1

        val aCustomer = Customer.create( //
            "Vinicius", //
            "(99) 0000-0000", //
            "test@mail.com", //
            Document.create(DocumentType.RG, "12345") //
        )

        val actualException = assertThrows<NotificationException> {
            aCustomer.update(expectedName, expectedPhone, expectedEmail, expectedDocument, expectedAddress)
        }

        assertAll(
            "Customer exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedAddressErrorMessage, actualException.getErrors().first().message) })
    }

    @Test
    fun `given invalid valid params with two errors when call update then should throw NotificationException`() {
        val expectedName = "Viniceta"
        val expectedPhone = "(12) 99999-9999"
        val expectedEmail = "vinicius@mail.com"
        val expectedDocument = Document.create(DocumentType.CPF, "12345")
        val expectedAddress = Address.create(
            "  ", "são paulo", "são paulo", "2333-4353", "brazil", "14 A"
        )
        val expectedErrorMessage = "Failed to update an Aggregate Customer"
        val expectedAddressErrorMessage = "'street' cannot be empty"
        val expectedEmailErrorMessage = "invalid 'email'"
        val expectedErrorCount = 2

        val aCustomer = Customer.create( //
            "Vinicius", //
            "(99) 0000-0000", //
            expectedEmail, //
            Document.create(DocumentType.RG, "12345") //
        )

        val actualException = assertThrows<NotificationException> {
            aCustomer.update(expectedName, expectedPhone, "", expectedDocument, expectedAddress)
        }

        assertAll(
            "Customer exception validation",
            { assertEquals(expectedErrorMessage, actualException.message) },
            { assertEquals(expectedErrorCount, actualException.getErrors().size) },
            { assertEquals(expectedEmailErrorMessage, actualException.getErrors()[0].message) },
            { assertEquals(expectedAddressErrorMessage, actualException.getErrors()[1].message) })
    }

}