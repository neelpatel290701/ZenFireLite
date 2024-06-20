package com.example.zenfirelite.datamodels

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
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
): Parcelable

@Parcelize
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
):Parcelable

@Parcelize
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
//    val value: Any?,
    val options: FieldOptionsModel?,
//    val reasons: List<Any?>?,
):Parcelable

@Parcelize
data class LayoutModel(
    val x: Long,
    val y: Long,
    val w: Long,
    val h: Long,
    val i: String,
    val fullWidth: Long?,
):Parcelable

@Parcelize
data class FieldOptionsModel(
    val radioOptions: List<RadioOptionModel>?,
    val dropdownOptions: List<DropdownOptionModel>?,
    val checkboxOptions: List<CheckboxOptionModel>?,
    val value: ValueModel?,
    val label: String?,
    val entity: String?,
    val entityType: String?,
    val columns: List<ColumnModel>?,
):Parcelable

@Parcelize
data class RadioOptionModel(
    val key: String,
    val value: String,
    val label: String,
):Parcelable

@Parcelize
data class DropdownOptionModel(
    val key: String,
    val value: String,
    val label: String,
):Parcelable

@Parcelize
data class CheckboxOptionModel(
    val key: String,
    val value: String,
    val label: String,
):Parcelable

@Parcelize
data class ValueModel(
    val entity: String,
    val entityType: String,
):Parcelable

@Parcelize
data class ColumnModel(
    val name: String,
    val displayName: String,
    val uiType: String,
    val dataType: String,
    val columnOptions: ColumnOptionsModel,
):Parcelable

@Parcelize
data class ColumnOptionsModel(
    val dropdownOptions: List<DropdownOption2Model>?,
):Parcelable

@Parcelize
data class DropdownOption2Model(
    val key: String,
    val value: String,
    val label: String,
):Parcelable

