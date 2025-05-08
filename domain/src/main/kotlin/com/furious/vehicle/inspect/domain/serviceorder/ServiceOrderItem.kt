package com.furious.vehicle.inspect.domain.serviceorder

import com.furious.vehicle.inspect.domain.Entity
import com.furious.vehicle.inspect.domain.exceptions.NotificationException
import com.furious.vehicle.inspect.domain.service.Service
import com.furious.vehicle.inspect.domain.utils.InstantUtils
import com.furious.vehicle.inspect.domain.validation.ValidationHandler
import com.furious.vehicle.inspect.domain.validation.handler.Notification
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.Instant

class ServiceOrderItem private constructor(
    anId: ServiceOrderItemID,
    aServiceOrder: ServiceOrderID,
    aService: Service,
    aServiceDescription: String, // Service description snapshot
    aPrice: BigDecimal, // Service default price or overridden price
    aQuantity: Int,
    aDiscountPercentage: BigDecimal = BigDecimal.ZERO,
    aSubTotal: BigDecimal,
    isCompleted: Boolean = false,
    aCompletedAt: Instant? = null
) : Entity<ServiceOrderItemID>(anId) {

    var serviceOrder: ServiceOrderID = aServiceOrder
        private set
    var service: Service = aService
        private set
    var serviceDescription: String = aServiceDescription
        private set
    var price: BigDecimal = aPrice
        private set
    var quantity: Int = aQuantity
        private set
    var discountPercentage: BigDecimal = aDiscountPercentage
        private set
    var subTotal: BigDecimal = aSubTotal
        private set
    var completed: Boolean = isCompleted
        private set
    var completedAt: Instant? = aCompletedAt
        private set

    init {
        selfValidate() // Post construct validate
    }

    companion object {
        fun create(
            aServiceOrder: ServiceOrderID,
            aService: Service,
            aServiceDescription: String? = null,
            aPrice: BigDecimal? = null,
            aDiscountPercentage: BigDecimal = BigDecimal.ZERO,
            aQuantity: Int
        ): ServiceOrderItem {
            val aDescription = aServiceDescription ?: aService.description
            val aPrice = aPrice ?: aService.defaultPrice ?: BigDecimal.ZERO
            val aFinalPrice = aPrice.subtract(
                aPrice.multiply(aDiscountPercentage.divide(BigDecimal(100))) //
                    .setScale(2, RoundingMode.HALF_UP) //
            )

            return ServiceOrderItem(
                ServiceOrderItemID.create(),
                aServiceOrder,
                aService,
                aDescription,
                aPrice,
                aQuantity,
                aDiscountPercentage,
                aFinalPrice.multiply(BigDecimal(aQuantity))
            )
        }

        fun with(aServiceOrderItem: ServiceOrderItem): ServiceOrderItem = ServiceOrderItem(
            aServiceOrderItem.id,
            aServiceOrderItem.serviceOrder,
            aServiceOrderItem.service,
            aServiceOrderItem.serviceDescription,
            aServiceOrderItem.price,
            aServiceOrderItem.quantity,
            aServiceOrderItem.discountPercentage,
            aServiceOrderItem.subTotal,
            aServiceOrderItem.completed,
            aServiceOrderItem.completedAt
        )
    }

    fun update(
        aService: Service,
        aServiceDescription: String,
        aPrice: BigDecimal,
        aDiscountPercentage: BigDecimal,
        aQuantity: Int,
        isCompleted: Boolean
    ): ServiceOrderItem {
        this.service = aService
        this.serviceDescription = aServiceDescription
        this.price = aPrice
        this.discountPercentage = aDiscountPercentage
        this.quantity = aQuantity
        if (isCompleted) {
            this.completed = true
            this.completedAt = InstantUtils.now()
        }
        calculateSubTotal()
        selfValidate(true)

        return this
    }

    fun complete(): ServiceOrderItem {
        this.completed = true
        this.completedAt = InstantUtils.now()
        return this
    }

    private fun calculateSubTotal() {
        val aPrice = this.price

        this.subTotal = aPrice.subtract(
            aPrice.multiply(this.discountPercentage.divide(BigDecimal(100))) //
                .setScale(2, RoundingMode.HALF_UP) //
        )
    }

    private fun selfValidate(isUpdate: Boolean = false) {
        val errorMessage = "Failed to ${if (isUpdate) "update" else "create"} an Entity Service Order Item"

        val notification = Notification.create()
        validate(notification)
        if (notification.hasError()) {
            throw NotificationException(errorMessage, notification)
        }
    }

    override fun validate(handler: ValidationHandler) {
        ServiceOrderItemValidator(this, handler).validate()
    }

    fun getId(): ServiceOrderItemID = id

}