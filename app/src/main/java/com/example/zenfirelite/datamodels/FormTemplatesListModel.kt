package com.example.zenfirelite.datamodels

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


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
    val fields: List<FieldModel>?,
    val description: String?,
)


data class FieldModel(
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
    val layout: LayoutModel?,
    val isActive: Boolean,
    val isDeleted: Boolean,
    val createdUserId: Long,
    val updatedUserId: Long,
    val createdAt: String,
    val updatedAt: String,
    var value: Any?,
    val options: FieldOptionsModel?,
    val reasons: List<ReasonValue?>?,
)


data class ReasonValue(
    val id: Long,
    val name: String,
    val displayName: String,
    val description: String?,
    val isSelected: Boolean,
    val reasonTemplateId: String,
    val isActive: Boolean,
    val isDeleted: Boolean,
    val createdUserId: Long,
    val updatedUserId: Long,
    val createdBy: String,
    val updatedBy: String,
    val createdAt: String,
    val updatedAt: String
)


data class LayoutModel(
    val x: Long,
    val y: Long,
    val w: Long,
    val h: Long,
    val i: String,
    val fullWidth: Long?,
)


data class FieldOptionsModel(
    val radioOptions: List<RadioOptionModel>?,
    val dropdownOptions: List<DropdownOptionModel>?,
    val checkboxOptions: List<CheckboxOptionModel>?,
    val value: ValueModel?,
    val label: String?,
    val entity: String?,
    val entityType: String?,
    val columns: List<ColumnModel>?,
)


data class RadioOptionModel(
    val key: String,
    val value: String,
    val label: String,
)


data class DropdownOptionModel(
    val key: String,
    val value: String,
    val label: String,
)


data class CheckboxOptionModel(
    val key: String,
    val value: String,
    val label: String,
)


data class ValueModel(
    val entity: String,
    val entityType: String,
)


data class ColumnModel(
    val name: String,
    val displayName: String,
    val uiType: String,
    val dataType: String?,
    val columnOptions: ColumnOptionsModel,
)


data class ColumnOptionsModel(
    val dropdownOptions: List<DropdownOption2Model>?,
)


data class DropdownOption2Model(
    val key: String,
    val value: String,
    val label: String,
)


