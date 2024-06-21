package com.example.zenfirelite.apis.datamodels

data class PreviousFormsResponse(
    val status: String,
    val requestId: String,
    val result: PreviousFormsResult,
)

data class PreviousFormsResult(
    val results: List<PreviousFormsResult2>,
    val count: Long,
)

data class PreviousFormsResult2(
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
    val createdBy: CreatedByResponse,
    val updatedBy: String,
    val createdAt: String,
    val updatedAt: String,
    val ticketId: Long,
    val ticketNumber: String,
)

data class CreatedByResponse(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val isAdmin: Boolean,
    val roleId: Long,
    val companyId: Long,
    val timeZoneId: Long?,
    val isActive: Boolean,
    val isDeleted: Boolean,
    val updatedBy: String,
    val updatedAt: String,
    val createdAt: String,
    val profilePhoto: String,
    val landline: String?,
    val cellphone: String?,
    val rateCriterion: String,
    val rateBasis: String,
    val about: String?,
    val objectId: String?,
)

