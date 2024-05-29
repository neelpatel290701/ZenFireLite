package com.example.zenfirelite.datamodels

sealed class FieldTypeListItem {
    data class EditTextType(val title: String , val inputType : String) : FieldTypeListItem()
    data class EditTextTypeNum(val title: String) : FieldTypeListItem()
    data class DropDownList(val title : String , val options : List<String>) : FieldTypeListItem()
    data class RadioButton(val title : String , val isRadioButton : Boolean) : FieldTypeListItem()
    data class RadioTypeButton(val title : String) : FieldTypeListItem()
}