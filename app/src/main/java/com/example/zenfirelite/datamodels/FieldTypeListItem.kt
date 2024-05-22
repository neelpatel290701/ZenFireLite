package com.example.zenfirelite.datamodels

sealed class FieldTypeListItem {
    data class EditTextType(val title: String) : FieldTypeListItem()
    data class EditTextTypeNum(val title: String) : FieldTypeListItem()
}