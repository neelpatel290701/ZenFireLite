package com.example.zenfirelite.apis.datamodels

data class CustomerListResponse(
    val status: String,
    val requestId: Any?,
    val result: CustomerListResult,
)

data class CustomerListResult(
    val data: Data,
)

data class Data(
    val hits: List<Hit>,
    val count: Long,
)

data class Hit(
    val id: Long,
    val firstname: String,
    val lastname: String,
    val additionalName: String,
    val name: String,
    val displayName: String,
    val customerIdentifier: String,
    val customerUniqueId: String,
    val email: String,
    val landline: String,
    val ext: String,
    val cellphone: String,
    val addressLine1: String,
    val addressLine2: String,
    val country: String,
    val city: String,
    val state: String,
    val zipcode: String,
    val additionalContacts: List<Any?>,
    val isProspect: Boolean,
    val addressTypeId: Long,
    val propertyTypeId: Long,
    val createdAt: String,
    val updatedAt: String,
    val isDeleted: Boolean,
    val isActive: Boolean,
    val doNotServe: Boolean,
    val customerId: Long,
    val rootCustomerId: Long,
    val createdUserId: Long,
    val isParent: Boolean,
    val companyId: Long,
    val notes: Notes,
    val searchString: String,
    val invoiceBalanceSum: Long,
    val hasPendingServiceAgreements: Boolean,
    val parentId: Long?,
)

data class Notes(
    val id: Long?,
    val text: String?,
)

