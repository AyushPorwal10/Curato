package com.curato.wallpapers.domain.common

data class PaginatedResult<T>(
    val items: List<T>,
    val currentPage: Int,
    val hasNextPage: Boolean,
    val totalResults: Int,
)

fun <T> PaginatedResult<T>.isEmpty() = items.isEmpty()
