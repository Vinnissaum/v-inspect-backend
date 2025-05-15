package com.furious.vehicle.inspect.application.customer.retrieve.list

import com.furious.vehicle.inspect.application.UseCase
import com.furious.vehicle.inspect.domain.pagination.Pagination
import com.furious.vehicle.inspect.domain.pagination.SearchQuery

abstract class ListCustomersUseCase : UseCase<SearchQuery, Pagination<ListCustomersOutput>>()