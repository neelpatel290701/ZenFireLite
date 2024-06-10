package com.example.zenfirelite.apis.datamodels

data class CustomerListRequestBody(
    val from: Long,
    val size: Long,
    val searchQuery: String,
    val mobile: String,
    val email: String,
    val status: String,
    val type: String,
    val switchSort: Boolean,
    val columnName: String,
    val createdUserId: String,
    val startingWith: String,
    val includeCreatedUser: Boolean,
    val isProspect: Boolean,
)
