package com.furious.vehicle.inspect.domain.customer

import com.furious.vehicle.inspect.domain.validation.AppError
import com.furious.vehicle.inspect.domain.validation.ValidationHandler
import com.furious.vehicle.inspect.domain.validation.Validator

class CustomerValidator(
    private val customer: Customer, aHandler: ValidationHandler
) : Validator(aHandler) {

    companion object {
        const val NAME_MAX_LENGTH = 255
        const val NAME_MIN_LENGTH = 3
    }

    override fun validate() {
        checkName()
        checkPhone()
        checkEmail()
        checkDocument()
        checkAddress()
    }

    private fun checkName() {
        val name = customer.name

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
            validationHandler().append(AppError("'name' must be between 3 and 255 characters"))
        }
    }

    private fun checkPhone() {
        val regex = Regex("^\\(?[1-9]{2}\\)? ?9?[0-9]{4}-?[0-9]{4}$")

        if (!regex.matches(customer.phone)) {
            validationHandler().append(AppError("invalid 'phone' format"))
        }
    }

    private fun checkEmail() {
        val regex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

        if (!regex.matches(customer.email)) {
            validationHandler().append(AppError("invalid 'email'"))
        }
    }

    private fun checkDocument() {
        customer.document.validate().forEach(validationHandler()::append)
    }

    private fun checkAddress() {
        if (customer.address == null) { // Address is optional, but if is present needs to be validated
            return
        }
        val errors = customer.address!!.validate()

        if (errors.isNotEmpty()) {
            errors.forEach(validationHandler()::append)
        }
    }

}