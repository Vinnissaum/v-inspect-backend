package com.furious.vehicle.inspect.domain.serviceorder

import com.furious.vehicle.inspect.domain.Entity
import com.furious.vehicle.inspect.domain.validation.ValidationHandler

class Checklist private constructor(anId: ChecklistID) : Entity<ChecklistID>(anId) {

    companion object {
        fun create(): Checklist = Checklist(ChecklistID())

        fun with(
            aChecklist: Checklist
        ): Checklist = Checklist(ChecklistID())

    }

    override fun validate(handler: ValidationHandler) {
        TODO("Not yet implemented")
    }

}