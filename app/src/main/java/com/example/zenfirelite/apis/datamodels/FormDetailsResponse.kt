package com.example.zenfirelite.apis.datamodels

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FormDetailsResponse(
    val status: String,
    val requestId: String,
    val result: FormDetailsResult
) : Parcelable

@Parcelize
data class FormDetailsResult(
    val id: Long,
    val name: String,
    val displayName: String,
    val isAnalysed: Boolean,
    val isActive: Boolean,
    val isDeleted: Boolean,
    val templateId: String,
    val companyId: Long,
    val createdUserId: Long,
    val updatedUserId: Long,
    val createdBy: CreatedBy,
    val updatedBy: UpdatedBy,
    val createdAt: String,
    val updatedAt: String,
    val sections: List<SectionDetails>
) : Parcelable

@Parcelize
data class CreatedBy(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val isAdmin: Boolean,
    val roleId: Long,
    val companyId: Long,
    val timeZoneId: Long,
    val isActive: Boolean,
    val isDeleted: Boolean,
    val updatedBy: String,
    val updatedAt: String,
    val createdAt: String,
    val profilePhoto: String,
    val landline: String,
    val cellphone: String,
    val rateCriterion: String,
    val rateBasis: String,
    val firstname: String,
    val lastname: String
) : Parcelable

@Parcelize
data class UpdatedBy(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val isAdmin: Boolean,
    val roleId: Long,
    val companyId: Long,
    val timeZoneId: Long,
    val isActive: Boolean,
    val isDeleted: Boolean,
    val updatedBy: String,
    val updatedAt: String,
    val createdAt: String,
    val profilePhoto: String,
    val landline: String,
    val cellphone: String,
    val rateCriterion: String,
    val rateBasis: String,
    val firstname: String,
    val lastname: String
) : Parcelable

@Parcelize
data class SectionDetails(
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
    val fields: List<FieldDetails>?,
    val description: String?
) : Parcelable

@Parcelize
data class FieldDetails(
    val id: String,
    val name: String,
    val displayName: String,
    val value: ValueDetailsOfForm?,
    val uiType: String,
    val dataType: String,
    val defaultValue: String?,
    val mandatory: Boolean,
    val readonly: Boolean,
    val sortPosition: String,
    val sectionId: String,
    val layout: LayoutDetails?,
    val isActive: Boolean,
    val isDeleted: Boolean,
    val createdUserId: Long,
    val updatedUserId: Long,
    val createdAt: String,
    val updatedAt: String,
    val reasons: List<ReasonDetails>,
    val options: FieldOptionsDetails?
) : Parcelable

@Parcelize
data class ReasonDetails(
    val id: Long,
    val name: String,
    val displayName: String,
    val description: String,
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
) : Parcelable

@Parcelize
data class LayoutDetails(
    val x: Long,
    val y: Long,
    val w: Long,
    val h: Long,
    val i: String,
    val fullWidth: Long?
) : Parcelable

@Parcelize
data class FieldOptionsDetails(
    val radioOptions: List<RadioOptionDetails>?,
    val dropdownOptions: List<DropdownOptionDetails>?,
    val checkboxOptions: List<CheckboxOptionDetails>?,
    val value: ValueDetails?,
    val label: String?,
    val entity: String?,
    val entityType: String?,
    val columns: List<ColumnDetails>?
) : Parcelable

@Parcelize
data class RadioOptionDetails(
    val key: String,
    val value: String,
    val label: String
) : Parcelable

@Parcelize
data class DropdownOptionDetails(
    val key: String,
    val value: String,
    val label: String
) : Parcelable

@Parcelize
data class CheckboxOptionDetails(
    val key: String,
    val value: String,
    val label: String
) : Parcelable

@Parcelize
data class ValueDetails(
    val entity: String,
    val entityType: String
) : Parcelable

@Parcelize
data class ColumnDetails(
    val name: String,
    val displayName: String,
    val uiType: String,
    val dataType: String,
    val columnOptions: ColumnOptionsDetails
) : Parcelable

@Parcelize
data class ColumnOptionsDetails(
    val dropdownOptions: List<DropdownOption2Details>?
) : Parcelable

@Parcelize
data class DropdownOption2Details(
    val key: String,
    val value: String,
    val label: String
) : Parcelable
