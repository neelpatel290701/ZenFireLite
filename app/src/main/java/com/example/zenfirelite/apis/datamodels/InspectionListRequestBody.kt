package com.example.zenfirelite.apis.datamodels

data class InspectionListRequestBody(
    val sortBy: SortBy,
)

data class SortBy(
    val createdAt: String,
)

