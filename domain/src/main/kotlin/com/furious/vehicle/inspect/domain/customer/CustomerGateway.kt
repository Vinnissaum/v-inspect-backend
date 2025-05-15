package com.furious.vehicle.inspect.domain.customer

import com.furious.vehicle.inspect.domain.pagination.Pagination
import com.furious.vehicle.inspect.domain.pagination.SearchQuery

interface CustomerGateway {

    fun create(aCustomer: Customer): Customer

    fun update(aCustomer: Customer): Customer

    fun deleteById(anId: CustomerID)

    fun findById(anId: CustomerID): Customer?

    fun findAll(aQuery: SearchQuery): Pagination<Customer>

}