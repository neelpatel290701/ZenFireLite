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
):Parcelable
