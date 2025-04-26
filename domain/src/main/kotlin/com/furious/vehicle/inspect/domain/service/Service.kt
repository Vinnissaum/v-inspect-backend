package com.furious.vehicle.inspect.domain.service

import com.furious.vehicle.inspect.domain.AggregateRoot
import com.furious.vehicle.inspect.domain.validation.ValidationHandler

class Service private constructor(anId: ServiceID) : AggregateRoot<ServiceID>(anId) {



    override fun validate(handler: ValidationHandler) {
        TODO("Not yet implemented")
    }
}