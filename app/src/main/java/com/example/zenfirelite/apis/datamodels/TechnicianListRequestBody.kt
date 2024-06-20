package com.example.zenfirelite.apis.datamodels

import com.google.gson.annotations.SerializedName

data class TechnicianListRequestBody(
    @SerializedName("isFromIOS")
    val isFromIos: Boolean,
    val options: technicianOptions,
    val lists: List<String>,
)

data class technicianOptions(
    val technician: Technician,
)

data class Technician(
    val roleId: List<Long>,
)