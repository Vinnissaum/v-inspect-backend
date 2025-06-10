package com.furious.vehicle.inspect.application.vehicle.retrieve.list

import com.furious.vehicle.inspect.domain.vehicle.VehicleGateway
import com.furious.vehicle.inspect.domain.pagination.Pagination
import com.furious.vehicle.inspect.domain.pagination.SearchQuery

class DefaultListVehicleUseCase(private val vehicleGateway: VehicleGateway) : ListVehicleUseCase() {

    override fun execute(input: SearchQuery): Pagination<ListVehicleOutput> = //
        vehicleGateway.findAll(input).map(
            ListVehicleOutput::from
        )

}