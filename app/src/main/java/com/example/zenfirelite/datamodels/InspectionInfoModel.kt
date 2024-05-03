package com.example.zenfirelite.datamodels

data class InspectionInfoModel(
    val InspectionNumber : String,
    val CustomerName : String,
    val InspectionStatus : String,
    val DeficiencyReported : String,
    val Recommendation : String,
    val InsStartDate : String,
    val InsEndDate : String,
    val InsStartTime : String,
    val InsEndTime : String,
    val InspectorName : String
){

}
