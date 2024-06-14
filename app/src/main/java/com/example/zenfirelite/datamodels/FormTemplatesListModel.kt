package com.example.zenfirelite.datamodels

import com.example.zenfirelite.apis.datamodels.CheckboxOption
import com.example.zenfirelite.apis.datamodels.DropdownOption
import com.example.zenfirelite.apis.datamodels.DropdownOption2
import com.example.zenfirelite.apis.datamodels.RadioOption
import com.example.zenfirelite.apis.datamodels.Value


data class FormTemplatesListModel(
    val id: String,
    val name: String,
    val displayName: String,
    val description: String?,
    val template: String?,
    val isActive: Boolean,
    val isDeleted: Boolean,
    val companyId: Long,
    val createdUserId: Long,
    val updatedUserId: Long,
    val createdAt: String,
    val updatedAt: String,
    val sections: List<SectionData>,
)


data class SectionData(
    val id: String,
    val name: String,
    val displayName: String,
    val showDisplayName: Boolean,
    val showSummary: Boolean,
    val moduleId: String,
    val moduleEntityId: String,
    val companyId: Long,
    val sortPosition: String,
    val isActive: Boolean,
    val isDeleted: Boolean,
    val createdUserId: Long,
    val updatedUserId: Long,
    val createdAt: String,
    val updatedAt: String,
    val fields: List<FieldType>?,
    val description: String?,
)

data class FieldType(
    val id: String,
    val name: String,
    val displayName: String,
    val uiType: String,
    val dataType: String,
    val defaultValue: String?,
    val mandatory: Boolean,
    val readonly: Boolean,
    val sortPosition: String,
    val sectionId: String,
    val layout: Layout?,
    val isActive: Boolean,
    val isDeleted: Boolean,
    val createdUserId: Long,
    val updatedUserId: Long,
    val createdAt: String,
    val updatedAt: String,
    val value: Any?,
    val options: FieldOptions?,
    val reasons: List<Any?>?,
)

data class Layout(
    val x: Long,
    val y: Long,
    val w: Long,
    val h: Long,
    val i: String,
    val fullWidth: Long?,
)

data class FieldOptions(
    val radioOptions: List<RadioOption>?,
    val dropdownOptions: List<DropdownOption>?,
    val checkboxOptions: List<CheckboxOption>?,
    val value: Value?,
    val label: String?,
    val entity: String?,
    val entityType: String?,
    val columns: List<Column>?,
)

data class RadioOption(
    val key: String,
    val value: String,
    val label: String,
)

data class DropdownOption(
    val key: String,
    val value: String,
    val label: String,
)

data class CheckboxOption(
    val key: String,
    val value: String,
    val label: String,
)

data class Value(
    val entity: String,
    val entityType: String,
)

data class Column(
    val name: String,
    val displayName: String,
    val uiType: String,
    val dataType: String,
    val columnOptions: ColumnOptions,
)

data class ColumnOptions(
    val dropdownOptions: List<DropdownOption2>?,
)

data class DropdownOption2(
    val key: String,
    val value: String,
    val label: String,
)


