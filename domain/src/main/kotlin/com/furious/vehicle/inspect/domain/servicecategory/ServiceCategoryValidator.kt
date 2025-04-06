package com.furious.vehicle.inspect.domain.servicecategory

import com.furious.vehicle.inspect.domain.validation.AppError
import com.furious.vehicle.inspect.domain.validation.ValidationHandler
import com.furious.vehicle.inspect.domain.validation.Validator

class ServiceCategoryValidator(private val aServiceCategory: ServiceCategory, aHandler: ValidationHandler) :
    Validator(aHandler) {

    companion object {
        private const val NAME_MAX_LENGTH = 255
        private const val NAME_MIN_LENGTH = 3
    }

    override fun validate() {
        checkName()
    }

    private fun checkName() {
        val name = aServiceCategory.name

        if (name.isEmpty()) {
            validationHandler().append(AppError("'name' cannot be empty"))
            return
        }

        if (name.isBlank()) {
            validationHandler().append(AppError("'name' cannot be blank"))
            return
        }

        val nameLength = name.trim().length
        if (nameLength < NAME_MIN_LENGTH || nameLength > NAME_MAX_LENGTH) {
            validationHandler().append(AppError("'name' must be between $NAME_MIN_LENGTH and $NAME_MAX_LENGTH characters"))
        }
    }
}