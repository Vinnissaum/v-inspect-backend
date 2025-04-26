package com.furious.vehicle.inspect.domain.service

import com.furious.vehicle.inspect.domain.AggregateRoot
import com.furious.vehicle.inspect.domain.exceptions.NotificationException
import com.furious.vehicle.inspect.domain.servicecategory.ServiceCategory
import com.furious.vehicle.inspect.domain.servicecategory.ServiceCategoryID
import com.furious.vehicle.inspect.domain.utils.InstantUtils
import com.furious.vehicle.inspect.domain.validation.ValidationHandler
import com.furious.vehicle.inspect.domain.validation.handler.Notification
import java.math.BigDecimal
import java.time.Instant

class Service private constructor(
    anId: ServiceID,
    aServiceCategory: ServiceCategoryID,
    aDescription: String,
    aPrice: BigDecimal?,
    createdAt: Instant,
    updatedAt: Instant
) : AggregateRoot<ServiceID>(anId) {
    var category: ServiceCategoryID = aServiceCategory
        private set
    var description: String = aDescription
        private set
    var price: BigDecimal? = aPrice
        private set
    var createdAt: Instant = createdAt
        private set
    var updatedAt: Instant = updatedAt
        private set

    init {
        selfValidate() // Post construct validate
    }

    companion object {
        fun create(aDescription: String, category: ServiceCategoryID, aPrice: BigDecimal? = null): Service = Service(
            ServiceID.unique(), category, aDescription, aPrice, InstantUtils.now(), InstantUtils.now()
        )

        fun with(aService: Service): Service = Service(
            aService.id, aService.category, aService.description, aService.price, aService.createdAt, aService.updatedAt
        )
    }

    fun update(aCategory: ServiceCategoryID, aDescription: String, aPrice: BigDecimal?) : Service {
        this.category = aCategory
        this.description = aDescription
        this.price = aPrice
        this.updatedAt = InstantUtils.now()

        selfValidate(true)
        return this
    }

    private fun selfValidate(isUpdate: Boolean = false) {
        val errorMessage = "Failed to ${if (isUpdate) "update" else "create"} an Aggregate Service"

        val notification = Notification.create()
        validate(notification)
        if (notification.hasError()) {
            throw NotificationException(errorMessage, notification)
        }
    }

    override fun validate(handler: ValidationHandler) {
        ServiceValidator(this, handler).validate()
    }

    fun getId(): ServiceID = id
}