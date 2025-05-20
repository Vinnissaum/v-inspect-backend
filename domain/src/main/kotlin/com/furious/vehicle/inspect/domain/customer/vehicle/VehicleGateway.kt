package com.furious.vehicle.inspect.domain.customer.vehicle

import com.furious.vehicle.inspect.domain.BaseGateway
import com.furious.vehicle.inspect.domain.customer.CustomerID

interface VehicleGateway : BaseGateway<Vehicle, VehicleID> {

    fun findByCustomerId(customerId: CustomerID): List<Vehicle>

}