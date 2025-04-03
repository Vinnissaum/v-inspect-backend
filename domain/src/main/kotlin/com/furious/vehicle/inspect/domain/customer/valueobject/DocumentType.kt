package com.furious.vehicle.inspect.domain.customer.valueobject

enum class DocumentType {
    CIN, CPF, RG, PASSPORT, CNH, OTHER;

    companion object {
        fun from(value: String): DocumentType {
            return entries.firstOrNull { it.name.equals(value, ignoreCase = true) }
                ?: throw IllegalArgumentException("Invalid DocumentType: $value")
        }
    }
}