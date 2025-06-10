package com.furious.vehicle.inspect.application.customer.retrieve.list

import com.furious.vehicle.inspect.domain.customer.CustomerGateway
import com.furious.vehicle.inspect.domain.pagination.Pagination
import com.furious.vehicle.inspect.domain.pagination.SearchQuery

class DefaultListCustomersUseCase(private val customerGateway: CustomerGateway) : ListCustomersUseCase() {

    override fun execute(input: SearchQuery): Pagination<ListCustomersOutput> =
        customerGateway.findAll(input).map { aCustomer ->
            ListCustomersOutput.from(aCustomer)
        }

}