package com.example.zenfirelite.datamodels

data class TicketFormListModel(
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
