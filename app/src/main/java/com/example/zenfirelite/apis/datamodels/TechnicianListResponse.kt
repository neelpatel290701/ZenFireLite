package com.example.zenfirelite.apis.datamodels

data class TechnicianListResponse(
    val status: String,
    val requestId: Any?,
    val result: TechnicianResult,
)

data class TechnicianResult(
    val technician: List<TechnicianInfo>,
)

data class TechnicianInfo(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val isAdmin: Long,
    val companyId: Long,
    val buId: Any?,
    val reportsToUserId: Any?,
    val timeZoneId: Long?,
    val languageId: Any?,
    val currencyId: Any?,
    val isActive: Long,
    val isDeleted: Long,
    val updatedBy: String,
    val updatedAt: String,
    val createdAt: String,
    val about: String?,
    val objectId: String?,
    val profilePhoto: String?,
    val landline: String?,
    val cellphone: String?,
    val ext: String?,
    val costRate: Any?,
    val billableRate: Any?,
    val rateCriterion: String,
    val rateBasis: String,
    val role: Long,
)

