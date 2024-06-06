package com.example.zenfirelite.apis.datamodels

data class UserAuthResponse(
    val status: String,
    val requestId: Any?,
    val result: Result,
)

data class Result(
    val user: User,
    val accessToken: String,
    val vapidPublic: String,
)

data class User(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val isAdmin: Boolean,
    val roleId: Long,
    val timeZoneId: Long,
    val isActive: Boolean,
    val isDeleted: Boolean,
    val updatedBy: String,
    val updatedAt: String,
    val createdAt: String,
    val costRate: Long,
    val billableRate: Long,
    val rateCriterion: String,
    val rateBasis: String,
    val company: Company,
    val roleDetails: RoleDetails,
    val profile: Profile,
    val profileId: Long,
    val permissions: Map<String, Any>,
    val role: Long,
    val firstname: String,
    val lastname: String,
    val profilePic: Boolean,
    val settings: Settings,
    val modulePreferences: List<Any?>,
    val hasSeenAnnouncement: Boolean,
)

data class Company(
    val id: Long,
    val name: String,
    val isActive: Boolean,
    val isDeleted: Boolean,
    val updatedAt: String,
    val createdAt: String,
    val localeIsocode: String,
    val vertical: String,
    val timezoneRegionName: String,
    val defaultLanguage: String,
    val defaultCurrencyCode: String,
    val dstEnabled: Boolean,
    val isMigrated: Boolean,
    val redirectDetails: RedirectDetails,
)

data class RedirectDetails(
    val ios: String,
    val web: String,
    val android: String,
)

data class RoleDetails(
    val id: Long,
    val name: String,
    val companyId: Long,
    val isActive: Boolean,
    val isDeleted: Boolean,
    val createdUserId: Long,
    val updatedUserId: Long,
    val updatedBy: String,
    val updatedAt: String,
    val createdAt: String,
)

data class Profile(
    val id: Long,
    val objectId: Long,
    val name: String,
    val isActive: Boolean,
    val isDeleted: Boolean,
    val createdUserId: Long,
    val updatedUserId: Long,
)

data class Settings(
    val id: String,
    val isCallModuleEnabled: Boolean,
    val isFieldManager: Boolean,
    val userCanAddTicket: Boolean,
    val allowAddVisitFromField: Boolean,
    val signupPrivilege: Boolean,
    val showScheduler: Boolean,
    val isSupportAccount: Boolean,
    val adminPrivilege: Boolean,
    val userId: Long,
    val createdAt: String,
    val updatedAt: String,
    val createdUserId: Long,
    val updatedUserId: Long,
    val companyId: Long,
)

