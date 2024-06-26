package com.example.zenfirelite.datamodels
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class InspectionListModel(
    val ticketId : Long,
    val InspectionNumber : String,
    val CustomerName : String,
    val InspectionStatus : String,
    val DeficiencyReported : String,
    val Recommendation : String,
    val InsStartDate : String,
    val InsEndDate : String,
    val InspectorName : String,
    val CustomerUniqueId : String,
    val serviceAddressId : Long,
    val ServiceAddressLine1 : String,
    val ServiceAddressLine2: String,
    val city : String,
    val state : String,
    val zipCode : String,
    val country : String
):Parcelable
