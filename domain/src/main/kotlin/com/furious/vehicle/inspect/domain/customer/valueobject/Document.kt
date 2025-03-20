package com.furious.vehicle.inspect.domain.customer.valueobject

import com.furious.vehicle.inspect.domain.ValueObject
import com.furious.vehicle.inspect.domain.validation.AppError

data class Document private constructor(
    val documentType: DocumentType, val value: String
) : ValueObject() {
    companion object {
        fun create(documentType: DocumentType, value: String): Document = Document(documentType, value.trim())
    }

    fun validate(): List<AppError> {
        if (value.trim().isBlank()) {
            return listOf(AppError("Document value cannot be blank"))
        }
        return listOf()
    }
}