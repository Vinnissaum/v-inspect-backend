package com.furious.vehicle.inspect.domain.serviceorder

import com.furious.vehicle.inspect.domain.AggregateRoot
import com.furious.vehicle.inspect.domain.customer.CustomerID
import com.furious.vehicle.inspect.domain.vehicle.VehicleID
import com.furious.vehicle.inspect.domain.utils.InstantUtils
import com.furious.vehicle.inspect.domain.validation.ValidationHandler
import java.math.BigDecimal
import java.time.Instant

class ServiceOrder private constructor(
    anId: ServiceOrderID,
    aCustomer: CustomerID,
    aVehicle: VehicleID,
    aCheckList: ChecklistID?,
    status: ServiceOrderStatus,
    aTotal: BigDecimal,
    aCreatedAt: Instant,
    anUpdatedAt: Instant,
    aCompletedAt: Instant? = null
) : AggregateRoot<ServiceOrderID>(anId) {
    var customer: CustomerID = aCustomer
        private set
    var vehicle: VehicleID = aVehicle
        private set
    var checkList: ChecklistID? = aCheckList
        private set
    var status: ServiceOrderStatus = status
        private set
    var total: BigDecimal = aTotal
        private set
    var createdAt: Instant = aCreatedAt
        private set
    var updatedAt: Instant = anUpdatedAt
        private set
    var completedAt: Instant? = aCompletedAt
        private set
    var items = mutableListOf<ServiceOrderItem>()
        private set

    companion object {
        fun create(
            aCustomer: CustomerID,
            aVehicle: VehicleID,
            aCheckList: ChecklistID? = null,
        ): ServiceOrder = ServiceOrder(
            ServiceOrderID.unique(),
            aCustomer,
            aVehicle,
            aCheckList,
            ServiceOrderStatus.PENDING,
            BigDecimal.ZERO,
            InstantUtils.now(),
            InstantUtils.now()
        )

        fun with(aServiceOrder: ServiceOrder): ServiceOrder = ServiceOrder(
            aServiceOrder.id,
            aServiceOrder.customer,
            aServiceOrder.vehicle,
            aServiceOrder.checkList,
            aServiceOrder.status,
            aServiceOrder.total,
            aServiceOrder.createdAt,
            aServiceOrder.updatedAt,
            aServiceOrder.completedAt
        )
    }

    fun addItem(
        aServiceOrderItem: ServiceOrderItem
    ): ServiceOrder {
        this.items.add(aServiceOrderItem)
        calculateTotal()
        this.updatedAt = InstantUtils.now()
        return this
    }

    fun removeItem(
        aServiceOrderItemId: ServiceOrderItemID
    ): ServiceOrder {
        this.items.removeIf { it.getId() == aServiceOrderItemId }
        calculateTotal()
        this.updatedAt = InstantUtils.now()
        return this
    }

    fun progress(): ServiceOrder {
        this.status = ServiceOrderStatus.IN_PROGRESS
        this.updatedAt = InstantUtils.now()
        return this
    }

    fun complete(): ServiceOrder {
        this.status = ServiceOrderStatus.COMPLETED
        val instant = InstantUtils.now()
        this.completedAt = instant
        this.updatedAt = instant
        return this
    }

    fun calculateTotal(): ServiceOrder {
        this.total = items.fold(BigDecimal.ZERO) { acc, item -> acc.add(item.subTotal) }
        return this
    }

    override fun validate(handler: ValidationHandler) {} // Nothing to validate for now

    fun getId(): ServiceOrderID = id
}