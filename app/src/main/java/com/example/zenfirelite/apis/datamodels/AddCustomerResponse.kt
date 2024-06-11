package com.example.zenfirelite.apis.datamodels


data class AddCustomerResponse(
    val status: String,
    val requestId: Any?,
    val result: customerDetailsResult,
)

data class customerDetailsResult(
    val id: Long,
    val companyId: Long,
    val displayName: String,
    val firstname: String,
    val lastname: String,
    val name: String,
    val additionalName: String,
    val landline: String,
    val ext: String,
    val cellphone: String,
    val email: String,
    val customerIdentifier: String,
    val customerUniqueId: String,
    val skipLevel: Boolean,
    val isActive: Boolean,
    val isDeleted: Boolean,
    val createdBy: String,
    val updatedBy: String,
    val updatedAt: String,
    val createdAt: String,
    val createdUserId: Long,
    val updatedUserId: Long,
    val readyForSync: Boolean,
    val isProspect: Boolean,
    val billingAddress: customerDeatailsBillingAddress,
    val serviceAddresses: List<customerDetailsServiceAddress>,
    val serviceAddress: ServiceAddress2,
)

data class customerDeatailsBillingAddress(
    val id: Long,
    val addressLine1: String,
    val addressLine2: String,
    val city: String,
    val state: String,
    val zipcode: String,
    val country: String,
    val companyId: Long,
    val isTaxable: Boolean,
    val customerId: Long,
    val propertyTypeId: Long,
    val addressTypeId: Long,
    val paymentTermId: String,
    val isActive: Boolean,
    val isDeleted: Boolean,
    val doNotServe: Boolean,
    val createdBy: String,
    val updatedBy: String,
    val updatedAt: String,
    val createdAt: String,
    val createdUserId: Long,
    val updatedUserId: Long,
    val additionalContacts: List<Any?>,
    val notes: customerDetailsNotes,
    val customerIdentifier: String,
    val customerUniqueId: String,
    val cellphone: String,
    val landline: String,
    val email: String,
    val displayName: String,
    val firstname: String,
    val lastname: String,
    val additionalName: String,
    val name: String,
    val isParent: Boolean,
    val isProspect: Boolean,
)

data class customerDetailsNotes(
    val text: String,
)

data class customerDetailsServiceAddress(
    val id: Long,
    val addressLine1: String,
    val addressLine2: String,
    val city: String,
    val state: String,
    val zipcode: String,
    val country: String,
    val companyId: Long,
    val isTaxable: Boolean,
    val customerId: Long,
    val propertyTypeId: Long,
    val addressTypeId: Long,
    val isActive: Boolean,
    val isDeleted: Boolean,
    val doNotServe: Boolean,
    val createdBy: String,
    val updatedBy: String,
    val updatedAt: String,
    val createdAt: String,
    val createdUserId: Long,
    val updatedUserId: Long,
    val taxZone: customerDetailsTaxZone,
    val metadata: Metadata,
    val additionalContacts: List<Any?>,
    val notes: customerDetailsNotes2,
    val customerIdentifier: String,
    val customerUniqueId: String,
    val cellphone: String,
    val landline: String,
    val email: String,
    val displayName: String,
    val firstname: String,
    val lastname: String,
    val additionalName: String,
    val name: String,
    val isParent: Boolean,
    val isProspect: Boolean,
    val rootCustomerId: Long,
)

data class customerDetailsTaxZone(
    val id: Long,
    val taxCodeId: String,
    val addressId: Long,
    val createdBy: String,
    val updatedBy: String,
    val updatedAt: String,
    val createdAt: String,
    val createdUserId: Long,
    val updatedUserId: Long,
)

data class Metadata(
    val id: String,
    val customerAddressDetailsId: Long,
    val createdUserId: Long,
    val updatedUserId: Long,
    val isDeleted: Boolean,
    val createdAt: String,
    val updatedAt: String,
)

data class customerDetailsNotes2(
    val text: String,
)

data class ServiceAddress2(
    val id: Long,
    val addressLine1: String,
    val addressLine2: String,
    val city: String,
    val state: String,
    val zipcode: String,
    val country: String,
    val companyId: Long,
    val isTaxable: Boolean,
    val customerId: Long,
    val propertyTypeId: Long,
    val addressTypeId: Long,
    val isActive: Boolean,
    val isDeleted: Boolean,
    val doNotServe: Boolean,
    val createdBy: String,
    val updatedBy: String,
    val updatedAt: String,
    val createdAt: String,
    val createdUserId: Long,
    val updatedUserId: Long,
    val taxZone: customerDetailsTaxZone2,
    val metadata: Metadata2,
    val additionalContacts: List<Any?>,
    val notes: Notes3,
    val customerIdentifier: String,
    val customerUniqueId: String,
    val cellphone: String,
    val landline: String,
    val email: String,
    val displayName: String,
    val firstname: String,
    val lastname: String,
    val additionalName: String,
    val name: String,
    val isParent: Boolean,
    val isProspect: Boolean,
    val rootCustomerId: Long,
)

data class customerDetailsTaxZone2(
    val id: Long,
    val taxCodeId: String,
    val addressId: Long,
    val createdBy: String,
    val updatedBy: String,
    val updatedAt: String,
    val createdAt: String,
    val createdUserId: Long,
    val updatedUserId: Long,
)

data class Metadata2(
    val id: String,
    val customerAddressDetailsId: Long,
    val createdUserId: Long,
    val updatedUserId: Long,
    val isDeleted: Boolean,
    val createdAt: String,
    val updatedAt: String,
)

data class Notes3(
    val text: String,
)

