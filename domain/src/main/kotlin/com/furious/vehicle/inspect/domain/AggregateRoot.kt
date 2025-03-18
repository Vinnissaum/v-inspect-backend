package com.furious.vehicle.inspect.domain

abstract class AggregateRoot<ID : Identifier>(id: ID) : Entity<ID>(id)