package com.furious.vehicle.inspect.application.vehicle.retrieve.getByCustomer

import com.furious.vehicle.inspect.domain.customer.CustomerID
import com.furious.vehicle.inspect.domain.vehicle.VehicleGateway

class DefaultGetVehicleByCustomerIdUseCase(private val vehicleGateway: VehicleGateway) : GetVehicleByCustomerIdUseCase() {

    override fun execute(input: String): List<GetVehicleByCustomerIdOutput> = //
        vehicleGateway.findByCustomerId(CustomerID.from(input)) //
            .map(GetVehicleByCustomerIdOutput::from)

}