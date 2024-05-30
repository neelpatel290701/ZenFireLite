package com.example.zenfirelite.datamodels

sealed class FieldTypeListItem {
    data class EditTextType(val title: String , val inputType : String) : FieldTypeListItem()
    data class EditTextTypeNum(val title: String) : FieldTypeListItem()
    data class DropDownList(val title : String , val options : List<String>) : FieldTypeListItem()
    data class RadioButton(val title : String , val isRadioButton : Boolean , val options : List<RadioButtonItem>) : FieldTypeListItem()
    data class RadioTypeButton(val title : String) : FieldTypeListItem()
    data class SignaturePadType(val title : String) : FieldTypeListItem()
}

data class Option(
    val value: String,
    val isSelected: Boolean
)

data class Field(
    val inputType: String,
    val title: String,
    val options: List<Option>? = null // This is optional and only relevant for certain input types
)

data class Section(
    val fields: List<Field>
)