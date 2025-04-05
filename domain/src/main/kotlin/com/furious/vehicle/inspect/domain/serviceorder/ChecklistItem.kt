package com.furious.vehicle.inspect.domain.serviceorder

import com.furious.vehicle.inspect.domain.Entity
import com.furious.vehicle.inspect.domain.validation.AppError
import com.furious.vehicle.inspect.domain.validation.ValidationHandler

class ChecklistItem private constructor(
    anId: ChecklistItemID, aDescription: String, aPhotoUrl: String?, aChecklistID: ChecklistID
) : Entity<ChecklistItemID>(anId) {

    var description: String = aDescription
        private set
    var photoUrl: String? = aPhotoUrl
        private set
    var checklistID: ChecklistID = aChecklistID
        private set

    companion object {
        private const val DESCRIPTION_MAX_LENGTH = 255
        private const val DESCRIPTION_MIN_LENGTH = 5
        fun create(aDescription: String, aPhotoUrl: String? = null, aChecklistID: ChecklistID) =
            ChecklistItem(ChecklistItemID.create(), aDescription, aPhotoUrl, aChecklistID)

        fun with(aChecklistItem: ChecklistItem) = ChecklistItem(
            aChecklistItem.id, aChecklistItem.description, aChecklistItem.photoUrl, aChecklistItem.checklistID
        )
    }

    override fun validate(handler: ValidationHandler) {
        validateDescriptionConstraints(handler)

        photoUrl?.ifEmpty {
            handler.append(AppError("'photoUrl' of checklist item cannot be empty"))
            return
        }

        photoUrl?.ifBlank {
            handler.append(AppError("'photoUrl' of checklist item cannot be blank"))
        }
    }

    private fun validateDescriptionConstraints(handler: ValidationHandler) {
        if (description.isEmpty()) {
            handler.append(AppError("'description' of checklist item cannot be empty"))
            return
        }

        if (description.isBlank()) {
            handler.append(AppError("'description' of checklist item cannot be blank"))
            return
        }

        val descriptionLength = description.trim().length
        if (descriptionLength < DESCRIPTION_MIN_LENGTH || descriptionLength > DESCRIPTION_MAX_LENGTH) {
            handler.append(
                AppError("'description' of checklist item must be between $DESCRIPTION_MIN_LENGTH and $DESCRIPTION_MAX_LENGTH characters")
            )
        }
    }

    fun getId() = this.id
}