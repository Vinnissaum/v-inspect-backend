package com.furious.vehicle.inspect.domain.serviceorder

import com.furious.vehicle.inspect.domain.Entity
import com.furious.vehicle.inspect.domain.exceptions.NotificationException
import com.furious.vehicle.inspect.domain.serviceorder.valueobject.ChecklistStatus
import com.furious.vehicle.inspect.domain.utils.InstantUtils
import com.furious.vehicle.inspect.domain.validation.ValidationHandler
import com.furious.vehicle.inspect.domain.validation.handler.Notification
import com.furious.vehicle.inspect.domain.vehicle.VehicleID
import java.time.Instant

class Checklist private constructor(
    anId: ChecklistID,
    aVehicle: VehicleID,
    aCustomerSignature: String?,
    status: ChecklistStatus,
    createdAt: Instant,
    updatedAt: Instant,
    items: List<ChecklistItem>,
) : Entity<ChecklistID>(anId) {

    var vehicleID: VehicleID = aVehicle
        private set
    var customerSignature: String? = aCustomerSignature
        private set
    var status: ChecklistStatus = status
        private set
    var createdAt: Instant = createdAt
        private set
    var updatedAt: Instant = updatedAt
        private set
    var items: MutableList<ChecklistItem> = items.toMutableList()
        private set

    init {
        selfValidate() // Post construct validate
    }

    companion object {
        fun create(aVehicle: VehicleID): Checklist = Checklist(
            ChecklistID.unique(),
            aVehicle,
            null,
            ChecklistStatus.PENDING,
            createdAt = InstantUtils.now(),
            updatedAt = InstantUtils.now(),
            items = emptyList()
        )

        fun with(
            aChecklist: Checklist
        ): Checklist = Checklist(
            aChecklist.id,
            aChecklist.vehicleID,
            aChecklist.customerSignature,
            aChecklist.status,
            aChecklist.createdAt,
            aChecklist.updatedAt,
            aChecklist.items
        )
    }

    fun sign(aSignature: String): Checklist {
        this.customerSignature = aSignature
        this.status = ChecklistStatus.SIGNED
        this.updatedAt = InstantUtils.now()
        selfValidate("Failed to sign the checklist")
        return this
    }

    fun addItem(aDescription: String, aPhotoUrl: String? = null): Checklist {
        val item = ChecklistItem.create(aDescription, aPhotoUrl, this.id)
        this.items.add(item)
        this.updatedAt = InstantUtils.now()
        selfValidate("Failed to add a checklist item")
        return this
    }

    fun updateItems(items: List<ChecklistItem>): Checklist {
        this.items.clear()
        this.items.addAll(items)
        this.updatedAt = InstantUtils.now()
        selfValidate("Failed to update the checklist items")
        return this
    }

    fun update(
        aCustomerSignature: String?, status: ChecklistStatus, items: List<ChecklistItem>
    ): Checklist {
        this.customerSignature = aCustomerSignature
        this.status = status
        this.items.clear()
        this.items.addAll(items)
        this.updatedAt = InstantUtils.now()
        selfValidate("Failed to update the checklist")
        return this
    }

    private fun selfValidate(message: String = "") {
        val errorMessage = message.ifEmpty { "Failed to create an Entity Checklist" }
        val notification: Notification = Notification.create()
        this.validate(notification)

        if (notification.hasError()) {
            throw NotificationException(errorMessage, notification)
        }
    }

    override fun validate(handler: ValidationHandler) {
        ChecklistValidator(this, handler).validate()
    }

    fun getId() = this.id
}