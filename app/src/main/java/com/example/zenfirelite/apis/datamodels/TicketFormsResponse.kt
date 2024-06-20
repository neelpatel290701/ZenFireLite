package com.example.zenfirelite.apis.datamodels

data class TicketFormsResponse(
    val status: String,
    val requestId: Any?,
    val result: TicketFormsResult,
)


data class TicketFormsResult(
    val hits: List<TicketFormsHit>,
    val count: Long,
)

data class TicketFormsHit(
    val ticketId: Long,
    val ticketNumber: String,
    val serviceAddressId: Long,
    val customerId: Long,
    val fpFormId: Long,
    val fpFormName: String,
    val fpFormDisplayName: String,
    val isAnalysed: Long,
    val fpFormTemplateId: String,
    val fpFormCreatedUserId: Long,
    val fpFormUpdatedUserId: Long,
    val fpFormCreatedAt: String,
    val fpFormUpdatedAt: String,
    val companyId: Long,
)
