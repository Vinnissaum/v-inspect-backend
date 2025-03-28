package com.furious.vehicle.inspect.domain.customer

import com.furious.vehicle.inspect.domain.validation.AppError
import com.furious.vehicle.inspect.domain.validation.ValidationHandler
import com.furious.vehicle.inspect.domain.validation.Validator

class CustomerValidator(
    private val aCustomer: Customer, aHandler: ValidationHandler
) : Validator(aHandler) {

    companion object {
        private const val NAME_MAX_LENGTH = 255
        private const val NAME_MIN_LENGTH = 3
    }

    override fun validate() {
        checkName()
        checkPhone()
        checkEmail()
        checkDocument()
        checkAddress()
    }

    private fun checkName() {
        val name = aCustomer.name

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

        if (!regex.matches(aCustomer.phone)) {
            validationHandler().append(AppError("invalid 'phone' format"))
        }
    }

    private fun checkEmail() {
        val regex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

        if (!regex.matches(aCustomer.email)) {
            validationHandler().append(AppError("invalid 'email'"))
        }
    }

    private fun checkDocument() {
        aCustomer.document.validate().forEach(validationHandler()::append)
    }

    private fun checkAddress() {
        if (aCustomer.address == null) { // Address is optional, but if is present needs to be validated
            return
        }
        val errors = aCustomer.address!!.validate()

        if (errors.isNotEmpty()) {
            errors.forEach(validationHandler()::append)
        }
    }

}