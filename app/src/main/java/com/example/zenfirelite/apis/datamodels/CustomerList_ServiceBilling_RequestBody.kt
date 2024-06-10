package com.example.zenfirelite.apis.datamodels

import com.google.gson.annotations.SerializedName

data class CustomerList_ServiceBilling_RequestBody(
    val from: Long,
    val size: Long,
    val searchQuery: String,
    val createdUserId: String,
    @SerializedName("getFromDB")
    val getFromDb: Boolean,
    val isProspect: Boolean,
    val includeCreatedUser: Boolean,
)
