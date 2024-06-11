package com.example.zenfirelite.apis.datamodels

data class AddCustomerRequestBody(
    val firstname: String,
    val lastname: String,
    val additionalName: String,
    val customerIdentifier: String,
    val email: String,
    val landline: String,
    val cellphone: String,
    val ext: String,
    val name: String,
    val rootParentId: Any?,
    val parentId: Any?,
    val flowInvoiceInParent: Boolean,
    val billingAddress: addCustomerBillingAddress,
    val serviceAddress: addCustomerServiceAddress,
)

data class addCustomerBillingAddress(
    val addressLine1: String,
    val addressLine2: String,
    val city: String,
    val state: String,
    val country: String,
    val zipcode: String,
    val addressType: String,
    val propertyType: String,
    val notes: addCustomerNotes,
    val paymentTerm: PaymentTerm,
    val additionalContacts: List<Any?>,
    val doNotServe: Boolean,
    val taxZone: TaxZone,
)

data class addCustomerNotes(
    val text: String,
)

data class PaymentTerm(
    val id: String,
)

data class TaxZone(
    val id: String,
    val name: String,
    val companyId: Long,
    val description: String,
    val isActive: Boolean,
    val isDeleted: Boolean,
    val isDefault: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val createdUserId: Long,
    val updatedUserId: Long,
    val salesTaxRates: List<SalesTaxRate>,
    val combineTax: Double,
)

data class SalesTaxRate(
    val id: String,
    val companyId: Long,
    val locationCode: String,
    val taxRateBreakUp: TaxRateBreakUp,
    val combineTax: Double,
    val description: String,
    val location: String,
    val country: String,
    val isActive: Boolean,
    val isDeleted: Boolean,
    val isDefault: Boolean,
    val taxAgencyId: String,
    val createdAt: String,
    val updatedAt: String,
    val createdUserId: Long,
    val updatedUserId: Long,
)

data class TaxRateBreakUp(
    val localTax: Double,
    val stateTax: Long,
)

data class addCustomerServiceAddress(
    val addressLine1: String,
    val addressLine2: String,
    val city: String,
    val state: String,
    val country: String,
    val zipcode: String,
    val addressType: String,
    val propertyType: String,
    val notes: addCustomerNotes2,
    val paymentTerm: PaymentTerm2,
    val additionalContacts: List<Any?>,
    val doNotServe: Boolean,
    val taxZone: TaxZone2,
)

data class addCustomerNotes2(
    val text: String,
)

data class PaymentTerm2(
    val id: String,
)

data class TaxZone2(
    val id: String,
    val name: String,
    val companyId: Long,
    val description: String,
    val isActive: Boolean,
    val isDeleted: Boolean,
    val isDefault: Boolean,
    val createdAt: String,
    val updatedAt: String,
    val createdUserId: Long,
    val updatedUserId: Long,
    val salesTaxRates: List<SalesTaxRate2>,
    val combineTax: Double,
)

data class SalesTaxRate2(
    val id: String,
    val companyId: Long,
    val locationCode: String,
    val taxRateBreakUp: TaxRateBreakUp2,
    val combineTax: Double,
    val description: String,
    val location: String,
    val country: String,
    val isActive: Boolean,
    val isDeleted: Boolean,
    val isDefault: Boolean,
    val taxAgencyId: String,
    val createdAt: String,
    val updatedAt: String,
    val createdUserId: Long,
    val updatedUserId: Long,
)

data class TaxRateBreakUp2(
    val localTax: Double,
    val stateTax: Long,
)

