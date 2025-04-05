package com.furious.vehicle.inspect.domain.serviceorder

import com.furious.vehicle.inspect.domain.validation.AppError
import com.furious.vehicle.inspect.domain.validation.ValidationHandler
import com.furious.vehicle.inspect.domain.validation.Validator

class ChecklistValidator(private val aChecklist: Checklist, aHandler: ValidationHandler) : Validator(aHandler) {

    override fun validate() {
        validateSignature()
        validateChecklistItems()
    }

    private fun validateChecklistItems() {
        if (aChecklist.items.isEmpty()) {
            return
        }

        aChecklist.items.forEach { item ->
            item.validate(validationHandler())
        }
    }

    private fun validateSignature() {
        val signature = aChecklist.customerSignature

        // Should validate only if the signature is not null
        signature?.ifEmpty { validationHandler().append(AppError("'customerSignature' of checklist cannot be empty")) }
    }

}