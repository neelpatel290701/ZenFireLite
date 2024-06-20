package com.example.zenfirelite.apis.datamodels

import com.google.gson.annotations.SerializedName

data class InspectionListRequestBody(
    val sortBy: SortBy,
)

data class SortBy(
    @SerializedName("created_at")
    val createdAt: String,
)

