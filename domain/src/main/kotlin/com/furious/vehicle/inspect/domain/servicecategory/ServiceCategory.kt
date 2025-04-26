package com.furious.vehicle.inspect.domain.servicecategory

import com.furious.vehicle.inspect.domain.AggregateRoot
import com.furious.vehicle.inspect.domain.exceptions.NotificationException
import com.furious.vehicle.inspect.domain.utils.InstantUtils
import com.furious.vehicle.inspect.domain.validation.ValidationHandler
import com.furious.vehicle.inspect.domain.validation.handler.Notification
import java.time.Instant

class ServiceCategory private constructor(
    anId: ServiceCategoryID, aName: String, aDescription: String?, aCreatedAt: Instant, anUpdatedAt: Instant
) : AggregateRoot<ServiceCategoryID>(anId) {

    var name: String = aName
        private set
    var description: String? = aDescription
        private set
    var createdAt: Instant = aCreatedAt
        private set
    var updatedAt: Instant = anUpdatedAt
        private set

    init {
        selfValidate() // Post construct validate
    }

    companion object {
        fun create(aName: String, aDescription: String? = null): ServiceCategory = ServiceCategory(
            ServiceCategoryID.unique(), aName, aDescription, InstantUtils.now(), InstantUtils.now()
        )

        fun with(aServiceCategory: ServiceCategory): ServiceCategory = ServiceCategory(
            aServiceCategory.id,
            aServiceCategory.name,
            aServiceCategory.description,
            aServiceCategory.createdAt,
            aServiceCategory.updatedAt
        )
    }

    fun update(aName: String, aDescription: String? = null): ServiceCategory {
        this.name = aName
        this.description = aDescription
        this.updatedAt = InstantUtils.now()
        selfValidate(true)

        return this
    }

    private fun selfValidate(isUpdate: Boolean = false) {
        val errorMessage = "Failed to ${if (isUpdate) "update" else "create"} an Aggregate ServiceCategory"

        val notification = Notification.create()
        validate(notification)
        if (notification.hasError()) {
            throw NotificationException(errorMessage, notification)
        }
    }

    override fun validate(handler: ValidationHandler) {
        ServiceCategoryValidator(this, handler).validate()
    }

    fun getId(): ServiceCategoryID = id
}