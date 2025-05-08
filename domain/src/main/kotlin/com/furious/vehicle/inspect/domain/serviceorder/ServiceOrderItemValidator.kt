package com.furious.vehicle.inspect.domain.serviceorder

import com.furious.vehicle.inspect.domain.validation.AppError
import com.furious.vehicle.inspect.domain.validation.ValidationHandler
import com.furious.vehicle.inspect.domain.validation.Validator

class ServiceOrderItemValidator(private val anItem: ServiceOrderItem, aHandler: ValidationHandler) :
    Validator(aHandler) {

    override fun validate() {
        checkQuantity()
        checkPrice()
        checkDiscountPercentage()
    }

    private fun checkQuantity() {
        val quantity = anItem.quantity

        if (quantity <= 0) {
            validationHandler().append(AppError("'quantity' must be greater than 0"))
        }
    }

    private fun checkPrice() {
        val price = anItem.price

        if (price <= 0.toBigDecimal()) {
            validationHandler().append(AppError("'price' must be greater than 0"))
        }
    }

    private fun checkDiscountPercentage() {
        val discountPercentage = anItem.discountPercentage

        if (discountPercentage < 0.toBigDecimal()) {
            validationHandler().append(AppError("'discountPercentage' must be greater than or equal to 0"))
        }
    }
}