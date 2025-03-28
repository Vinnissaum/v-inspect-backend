package com.furious.vehicle.inspect.domain.customer

import com.furious.vehicle.inspect.domain.AggregateRoot
import com.furious.vehicle.inspect.domain.customer.valueobject.Address
import com.furious.vehicle.inspect.domain.customer.valueobject.Document
import com.furious.vehicle.inspect.domain.exceptions.NotificationException
import com.furious.vehicle.inspect.domain.utils.InstantUtils
import com.furious.vehicle.inspect.domain.validation.ValidationHandler
import com.furious.vehicle.inspect.domain.validation.handler.Notification
import java.time.Instant

class Customer private constructor(
    anId: CustomerID,
    aName: String,
    aPhone: String,
    anEmail: String,
    aDocument: Document,
    anAddress: Address?,
    aCreatedAt: Instant,
    anUpdatedAt: Instant
) : AggregateRoot<CustomerID>(anId) {

    var name: String = aName
        private set
    var phone: String = aPhone
        private set
    var email: String = anEmail
        private set
    var document: Document = aDocument
        private set
    var address: Address? = anAddress
        private set
    var createdAt: Instant = aCreatedAt
        private set
    var updatedAt: Instant = anUpdatedAt
        private set

    init {
        selfValidate() // Post construct validate
    }

    companion object {
        fun create(
            aName: String, aPhone: String, anEmail: String, aDocument: Document, anAddress: Address? = null
        ): Customer = Customer(
            CustomerID.unique(),
            aName,
            aPhone,
            anEmail,
            aDocument,
            anAddress,
            aCreatedAt = InstantUtils.now(),
            anUpdatedAt = InstantUtils.now()
        )

        fun with(
            aCustomer: Customer
        ): Customer = Customer(
            aCustomer.id,
            aCustomer.name,
            aCustomer.phone,
            aCustomer.email,
            aCustomer.document,
            aCustomer.address,
            aCustomer.createdAt,
            aCustomer.updatedAt
        )

    }

    fun update(
        aName: String, aPhone: String, anEmail: String, aDocument: Document, anAddress: Address? = null
    ): Customer {
        name = aName
        phone = aPhone
        email = anEmail
        document = aDocument
        address = anAddress
        updatedAt = InstantUtils.now()
        selfValidate(isUpdate = true)

        return this
    }

    override fun validate(handler: ValidationHandler) {
        CustomerValidator(this, handler).validate()
    }

    private fun selfValidate(isUpdate: Boolean = false) {
        val errorMessage = "Failed to ${if (isUpdate) "update" else "create"} an Aggregate Customer"
        val notification: Notification = Notification.create()
        this.validate(notification)

        if (notification.hasError()) {
            throw NotificationException(errorMessage, notification)
        }
    }

    fun getId() = this.id

}