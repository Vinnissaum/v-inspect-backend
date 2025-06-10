package com.furious.vehicle.inspect.domain

import com.furious.vehicle.inspect.domain.pagination.Pagination
import com.furious.vehicle.inspect.domain.pagination.SearchQuery

interface BaseGateway<T : Entity<ID>, ID : Identifier> {

    fun create(entity: T): T

    fun update(entity: T): T

    fun deleteById(id: ID)

    fun findById(id: ID): T?

    fun findAll(query: SearchQuery): Pagination<T>

}