package com.github.watabee.anilist.core.model

data class PageInfo(
    val total: Int,
    val perPage: Int,
    val currentPage: Int,
    val lastPage: Int,
    val hasNextPage: Boolean
)
