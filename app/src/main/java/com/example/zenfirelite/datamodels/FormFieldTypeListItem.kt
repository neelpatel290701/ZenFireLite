package com.example.zenfirelite.datamodels

sealed class FormFieldTypeListItem {
    data class EditTextType(val title: String , val inputType : String ,val value : String , val isTextArea : Boolean) : FormFieldTypeListItem()
    data class EditTextTypeNum(val title: String) : FormFieldTypeListItem()
    data class DropDownList(val title : String , val options : List<String>) : FormFieldTypeListItem()
    data class RadioButton(val title : String , val isRadioButton : Boolean , val options : List<RadioButtonItem>) : FormFieldTypeListItem()
    data class RadioTypeButton(val title : String) : FormFieldTypeListItem()
    data class SignaturePadType(val title : String) : FormFieldTypeListItem()
    data class TableView(val title : String) : FormFieldTypeListItem()
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
