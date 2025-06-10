package com.furious.vehicle.inspect.application.vehicle.retrieve.list

import com.furious.vehicle.inspect.application.UseCase
import com.furious.vehicle.inspect.domain.pagination.Pagination
import com.furious.vehicle.inspect.domain.pagination.SearchQuery

abstract class ListVehicleUseCase : UseCase<SearchQuery, Pagination<ListVehicleOutput>>()