package com.example.zenfirelite.apis.datamodels

import com.google.gson.annotations.SerializedName

data class BussinessInformationResponse(
    val status: String,
    val requestId: Any?,
    val result: BussinessInfoResult,
)

data class BussinessInfoResult(
    val id: Long,
    val addressTypeId: Long,
    val addressLevel1: String,
    val city: String,
    val state: String,
    val country: String,
    val zipcode: String,
    val isActive: Boolean,
    val isDeleted: Boolean,
    val logo: Logo,
    val createdUserId: Long,
    val updatedUserId: Long,
    val updatedAt: String,
    val createdAt: String,
    val name: String,
    val tagLine: String,
    @SerializedName("localeISOCode")
    val localeIsocode: String,
    val defaultLanguage: String,
    val defaultCurrencyCode: String,
    val dstEnabled: Boolean,
    val isMigrated: Boolean,
    val redirectDetails: BussinessInfoRedirectDetails,
    val media: Media,
    val administratorEmail: String,
    val administrator: Administrator,
    val businessUnitAddresses: List<Any?>,
)

data class Logo(
    val id: String,
    val altText: String,
    val file: String,
    val type: String,
    val moduleId: String,
    val moduleEntityId: String,
    val isActive: Boolean,
    val isDeleted: Boolean,
    val createdAt: String,
    val updatedAt: String,
)

data class BussinessInfoRedirectDetails(
    @SerializedName("IOS")
    val ios: String,
    @SerializedName("WEB")
    val web: String,
    @SerializedName("ANDROID")
    val android: String,
)

data class Media(
    val id: String,
    val altText: String,
    val file: String,
    val type: String,
    val moduleId: String,
    val moduleEntityId: String,
    val isActive: Boolean,
    val isDeleted: Boolean,
    val createdAt: String,
    val updatedAt: String,
)

data class Administrator(
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
    val about: String,
    val profilePhoto: ProfilePhoto,
    val landline: String,
    val cellphone: String,
    val rateCriterion: String,
    val rateBasis: String,
)

data class ProfilePhoto(
    val id: String,
    val altText: String,
    val file: String,
    val type: String,
    val moduleId: String,
    val moduleEntityId: String,
    val companyId: Long,
    val isActive: Boolean,
    val isDeleted: Boolean,
    val createdAt: String,
    val updatedAt: String,
)
