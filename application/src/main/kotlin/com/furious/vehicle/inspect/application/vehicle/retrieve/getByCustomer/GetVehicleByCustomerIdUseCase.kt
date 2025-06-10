package com.furious.vehicle.inspect.application.vehicle.retrieve.getByCustomer

import com.furious.vehicle.inspect.application.UseCase

abstract class GetVehicleByCustomerIdUseCase : UseCase<String, List<GetVehicleByCustomerIdOutput>>()