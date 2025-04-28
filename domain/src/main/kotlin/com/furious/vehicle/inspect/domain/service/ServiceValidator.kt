package com.furious.vehicle.inspect.domain.service

import com.furious.vehicle.inspect.domain.validation.AppError
import com.furious.vehicle.inspect.domain.validation.ValidationHandler
import com.furious.vehicle.inspect.domain.validation.Validator
import java.math.BigDecimal

class ServiceValidator(private val aService: Service, aHandler: ValidationHandler) : Validator(aHandler) {

    companion object {
        private const val DESCRIPTION_MAX_LENGTH = 255
        private const val DESCRIPTION_MIN_LENGTH = 3
    }

    override fun validate() {
        checkDescription()
        checkPrice()
    }

    private fun checkDescription() {
        val description = aService.description

        if (description.isEmpty()) {
            validationHandler().append(AppError("'description' cannot be empty"))
            return
        }

        if (description.isBlank()) {
            validationHandler().append(AppError("'description' cannot be blank"))
            return
        }

        val length = description.trim().length
        if (length < DESCRIPTION_MIN_LENGTH || length > DESCRIPTION_MAX_LENGTH) {
            validationHandler().append(
                AppError(
                    "'description' must be between $DESCRIPTION_MIN_LENGTH and $DESCRIPTION_MAX_LENGTH characters"
                )
            )
        }
    }

    private fun checkPrice() {
        val price = aService.defaultPrice

        if (price != null && price < BigDecimal.ZERO) {
            validationHandler().append(AppError("'price' cannot be negative"))
        }
    }

}