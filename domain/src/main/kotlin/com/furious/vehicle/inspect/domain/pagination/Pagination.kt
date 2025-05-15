package com.furious.vehicle.inspect.domain.pagination

data class Pagination<T>(
    val currentPage: Int, //
    val perPage: Int, //
    val total: Long, //
    val items: List<T> //
) {
    fun <R> map(mapper: (T) -> R): Pagination<R> {
        val newList = items.map(mapper)
        return Pagination(currentPage, perPage, total, newList)
    }
}