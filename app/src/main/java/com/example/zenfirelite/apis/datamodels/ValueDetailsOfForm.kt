package com.example.zenfirelite.apis.datamodels
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class ValueDetailsOfForm : Parcelable {
    @Parcelize
    data class StringValue(val value: String) : ValueDetailsOfForm()
    @Parcelize
    data class ListValue(val value: List<String>) : ValueDetailsOfForm()
}