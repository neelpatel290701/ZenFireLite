package com.example.zenfirelite.datamodels

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
@Parcelize
data class CustomerListModel(
    val firstName : String,
    val lastName : String,
    val customerUniqueId : String,
    val addressLine1 : String,
    val addressLine2: String,
    val city : String,
    val state : String,
    val zipCode : String,
    val email: String,
    val cellPhone : String,
    val landline : String,
): Parcelable
