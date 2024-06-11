package com.example.zenfirelite.datamodels
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InspectionListModel(
    val InspectionNumber : String,
    val CustomerName : String,
    val InspectionStatus : String,
    val DeficiencyReported : String,
    val Recommendation : String,
    val InsStartDate : String,
    val InsEndDate : String,
    val InspectorName : String,
    val CustomerUniqueId : String,
    val addressLine1 : String,
    val addressLine2: String,
    val city : String,
    val state : String,
    val zipCode : String,
    val country : String
):Parcelable
