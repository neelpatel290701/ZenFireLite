package com.example.zenfirelite.apis.datamodels

data class TicketFormsRequestBody(
    val terms: List<Term>,
)

data class Term(
    val ticketId: List<String>,
)

