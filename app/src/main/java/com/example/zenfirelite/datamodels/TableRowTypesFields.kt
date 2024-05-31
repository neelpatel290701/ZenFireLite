package com.example.zenfirelite.datamodels

sealed class TableRowTypesFields{

    data class HeaderRow(val title : String) : TableRowTypesFields()
    data class DataRow(val title : String) : TableRowTypesFields()
}
